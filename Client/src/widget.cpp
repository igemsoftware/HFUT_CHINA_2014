/*! \file widget.cpp
 * \brief implement the Widget class
 *
 * \author Bowen
 * \version 1.0
 * \date 2014.09.30
 */

#define MAGICNUMBER 0x08280112

#include "widget.h"
#include "search.h"
#include "userhistory.h"
#include <QHBoxLayout>
#include <QVBoxLayout>

#include <QDebug>
#include <QDir>
#include <QFile>
#include <QDialog>
#include <QMessageBox>
#include <QFileDialog>
#include <QDataStream>
#include <QStringList>
#include <QFileDevice>
#include <QRegExp>
#include <QDesktopServices>
#include <QHostAddress>

#include <curl/curl.h>
#include <boost/property_tree/ptree.hpp>
#include <boost/property_tree/json_parser.hpp>
#include <boost/foreach.hpp>

#include <stdio.h>

Widget::Widget(QWidget *parent)
    : QWidget(parent), ui(new Ui::Form), m_projectName("New Project")
{    
#ifdef Q_WS_WIN
    m_filePath = QDir::homePath();
#else
    m_filePath = QDir::homePath() + "/Desktop/";
#endif
    m_projectFile = new QFile(m_filePath + m_projectName + ".bd");
    ui->setupUi(this);
    client = new QTcpSocket;

    setFixedSize(1200, 760);
    //connect(ui->checkButton, SIGNAL(clicked()), this, SLOT(checkButtonClicked()));
    connect(ui->saveButton, SIGNAL(clicked()), this, SLOT(saveButtonClicked()));
    connect(ui->exportButton, SIGNAL(clicked()), this, SLOT(exportButtonClicked()));
    connect(ui->brickInput, SIGNAL(textChanged(QString)), this, SLOT(searchBoxTyped(QString)));
    connect(ui->newButton, SIGNAL(clicked()), this, SLOT(newButtonClicked()));
    connect(ui->openButton, SIGNAL(clicked()), this, SLOT(openButtonClicked()));
    connect(ui->desginPanel, SIGNAL(relevantActived(QStringList*)), this, SLOT(showRelevantBioBricks(QStringList*)));
    connect(ui->desginPanel, SIGNAL(infoActived(QString)), this, SLOT(showBioBrickInfo(QString)));
    connect(ui->brickList, SIGNAL(infoActived(QString)), this, SLOT(showBioBrickInfo(QString)));
    connect(ui->desginPanel, SIGNAL(biobrickadded()), this, SLOT(showUserHistory()));
    connect(client, SIGNAL(connected()), this, SLOT(serverConnected()));
    connect(client, SIGNAL(disconnected()), this, SLOT(serverDisconnected()));
    ui->brickInput->setPlaceholderText("Search BioBrick");
    ui->functionInput->setPlaceholderText("Input Function");
    ui->functionInput->setFixedSize(200,30);
    ui->brickInput->setFixedSize(229, 36);
    ui->biobrickInfo->setWordWrap(false);
    ui->biobrickInfo->setTextInteractionFlags(Qt::LinksAccessibleByMouse | Qt::LinksAccessibleByKeyboard);
    ui->biobrickInfo->setOpenExternalLinks(true);
    ui->biobrickInfo->setTextFormat(Qt::RichText);
    //ui->brickList->setFixedSize(250, 450);

    ui->historys->setFixedHeight(151);
    ui->saveButton->setFixedSize(60, 25);
    //ui->checkButton->setFixedSize(60, 25);
    ui->openButton->setFixedSize(60, 25);
    ui->newButton->setFixedSize(60, 25);
    ui->exportButton->setFixedSize(60, 25);
    ui->historys->setFocusPolicy(Qt::NoFocus);
    setWindowTitle(m_projectName + "-" + "BioFunctional Designer");
    ui->functionInput->setFocus();
    client->connectToHost(QHostAddress("http://210.45.250.5"), 8080);
    client->startTimer(5);
    if (client->isValid() ){
        ui->statusLabel->setText("Connected");
    }else{
        ui->statusLabel->setText("Disconnected");
    }
    UserHistory::getUserHistory();
    ui->historys->setSpacing(10);
    ui->historys->setViewMode(QListView::IconMode);
    ui->historys->setWrapping(false);
    ui->historys->setFlow(QListView::LeftToRight);
    ui->historys->setMouseTracking(true);

    ui->historys->setAlternatingRowColors(false);
    showUserHistory();

}

void Widget::showUserHistory(){
    ui->historys->clear();
    QStringList* history = UserHistory::getUserHistory();
    if (history == NULL){
        return;
    }

    for (int i = 0; i < history->size(); i++){
        QListWidgetItem* item = new QListWidgetItem();
        QWidget* labelContainer = new QWidget();
        QVBoxLayout* layout = new QVBoxLayout();
        QLabel* label = new QLabel();
        QStringList infos = history->at(i).split("|");
        QString tips = "Name: " + infos.at(0) + "\nType: " + infos.at(1) + "\nURL: " + infos.at(2);
        label->setProperty("recommendBioBrick", true);
        label->setToolTip(tips);
        QString name = infos.at(0);
        label->setText(name);
        label->setFixedSize(167, 29);
        label->setAlignment(Qt::AlignCenter);
        layout->addWidget(label);
        labelContainer->setLayout(layout);
        //qDebug() << biobrickCount << endl;
        item->setSizeHint(QSize(177, 40));
        item->setWhatsThis("recommend");
        ui->historys->addItem(item);
        ui->historys->setItemWidget(item, labelContainer);
    }

}

Widget::~Widget()
{

}

void Widget::keyPressEvent(QKeyEvent *e){
    switch(e->key()){
    case Qt::Key_D:
        break;
    }
}

void Widget::searchBoxTyped(QString /*text*/){
    ui->brickList->cleanList();
    performSearch();
}



void Widget::saveButtonClicked(){
    saveProject();
}

void Widget::exportButtonClicked(){
    exportResult();
}

void Widget::saveProject(){
    if (!m_projectFile->open(QIODevice::WriteOnly)){
        QMessageBox::warning(this, tr("BioFunctional Designer"), tr("Can not write file %1:\n%2.")
                             .arg(m_projectName)
                             .arg(m_projectFile->errorString()));
        return;
    }
    m_projectFile->write("");
    QDataStream out(m_projectFile);
    out.setVersion(QDataStream::Qt_5_3);

    out << quint32(MAGICNUMBER);
    QApplication::setOverrideCursor(Qt::WaitCursor);
    const QStringList& biobricks = ui->desginPanel->getBioBrickNames();
    for (int i = 0; i < biobricks.size(); i++){
        out << quint16(i) << biobricks.at(i);
    }
    QApplication::restoreOverrideCursor();
    m_projectFile->close();
    return;

}

void Widget::exportResult(){
    QFile imageFile(QString::fromStdString(m_filePath.toStdString().substr(0, m_filePath.size()-3)) + ".png");
    if (imageFile.open(QIODevice::WriteOnly)){
        QDataStream out(&imageFile);
        ui->desginPanel->getResultImage(ui->functionInput->text())->save(out.device());
    }
}

void Widget::newButtonClicked(){
    saveProject();
    QString temp = m_filePath;
    m_filePath = QFileDialog::getSaveFileName(this, tr("Create New Project"), m_filePath, tr("BD Files (*.bd)"));
    if (!m_filePath.isNull()){
        m_projectFile = new QFile(m_filePath);
        m_projectName = m_projectFile->fileName();
        setWindowTitle(m_projectName + "-" + "BioFunctional Designer");
    }else{
        m_filePath = temp;
    }

}

void Widget::openButtonClicked(){
    saveProject();
    QString temp = m_filePath;
    m_filePath = QFileDialog::getOpenFileName(this, tr("Open Project"), m_filePath, tr("BD Files (*.bd)"));
    m_projectFile = new QFile(m_filePath);
    if (m_projectFile == NULL || !m_projectFile->exists()){
        QMessageBox::warning(this, "No Such File", "File not found");
        m_filePath = temp;
        m_projectFile = new QFile(m_filePath);
    }
    else{
        readProject();
    }
}

void Widget::readProject(){
    if (!m_projectFile->open(QIODevice::ReadOnly)){
        QMessageBox::warning(this, tr("BioFunctional Designer"), tr("Can not read file %1:\n%2.")
                             .arg(m_projectName)
                             .arg(m_projectFile->errorString()));
        return;
    }
    QDataStream in(m_projectFile);
    in.setVersion(QDataStream::Qt_5_3);
    quint32 magic;
    in >> magic;
    if (magic != MAGICNUMBER){
        QMessageBox::warning(this, tr("BioFunctional Designer"), tr("This file is not a BioFunctional Designer file."));
        return;
    }

    cleanWidget();
    quint16 index;
    QString biobrick;
    QApplication::setOverrideCursor(Qt::WaitCursor);
    while(!in.atEnd()){
        in >> index >> biobrick;
        ui->desginPanel->insertBioBrick(index, biobrick);
    }
    QApplication::restoreOverrideCursor();
    m_projectFile->close();
    ui->desginPanel->rePaintPanel();
}
std::string resultString = "";
void Widget::cleanWidget(){
    ui->desginPanel->clean();
}

void Widget::performSearch(){

    QStringList* results = Search::searchBioBrick(ui->brickInput->text(), ui->desginPanel->getBioBrickNames());
    if (results == NULL){
        return;
    }

    for (int i = 0; i < results->size(); i++){
        ui->brickList->addBioBrick(results->at(i));
    }

}

void Widget::showRelevantBioBricks(QStringList *biobricks){

    ui->relevantList->clear();
    for (int i = 0; i < biobricks->size(); i++){
        ui->relevantList->addBioBrick(biobricks->at(i));
    }
}

void Widget::showBioBrickInfo(QString info){
    ui->biobrickInfo->setText(info);
}

void Widget::serverConnected(){
    //qDebug()<< "connected" << endl;
    ui->statusLabel->setText("Connected");
}

void Widget::serverDisconnected(){
    //qDebug()<< "disconnected" << endl;
    ui->statusLabel->setText("Disconnected");
}
