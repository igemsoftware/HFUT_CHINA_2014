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
    ui->brickInput->setPlaceholderText("Search BioBrick");
    ui->functionInput->setPlaceholderText("Input Function");
    ui->functionInput->setFixedSize(200,30);
    ui->brickInput->setFixedSize(229, 36);
    ui->biobrickInfo->setOpenExternalLinks(true);
    ui->biobrickInfo->setWordWrap(false);
    //ui->biobrickInfo->setTextInteractionFlags(Qt::TextSelectableByMouse);
    ui->biobrickInfo->setTextFormat(Qt::RichText);
    //ui->brickList->setFixedSize(250, 450);

    ui->recomendBrick->setFixedHeight(151);
    ui->saveButton->setFixedSize(60, 25);
    //ui->checkButton->setFixedSize(60, 25);
    ui->openButton->setFixedSize(60, 25);
    ui->newButton->setFixedSize(60, 25);
    ui->exportButton->setFixedSize(60, 25);
    ui->recomendBrick->setFocusPolicy(Qt::NoFocus);
    setWindowTitle(m_projectName + "-" + "BioFunctional Designer");
    ui->functionInput->setFocus();
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

void Widget::searchBoxTyped(QString text){
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

/*

size_t processHTTPRes(void* buffer, size_t size, size_t nmenb, void* stream){
    resultString += (char*)buffer;
    //qDebug() << QString::fromStdString(resultString) << endl;

    return size* nmenb;
}

QStringList Widget::getReslutList(const QString &requestType, const QStringList &prama){
    std::string url = getRequestUrl(requestType, prama);
    CURL* curl;
    CURLcode res;
    struct curl_slist* headers = NULL;
    headers = curl_slist_append(headers, "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*;q=0.8");
    headers = curl_slist_append(headers, "Accept-Encoding:	gzip, deflate");
    headers = curl_slist_append(headers, "Accept-Language:	zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
    headers = curl_slist_append(headers, "Connection:	keep-alive");
    headers = curl_slist_append(headers, "User-Agent:	Mozilla/5.0 Gecko/20100101 Firefox/32.0");

    curl = curl_easy_init();
    if (curl){
        curl_easy_setopt(curl, CURLOPT_HTTPHEADER, headers);
        curl_easy_setopt(curl, CURLOPT_URL, url.c_str());
        curl_easy_setopt(curl, CURLOPT_HTTPGET, url.c_str());
        curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, &processHTTPRes);
        res = curl_easy_perform(curl);
        if (res != 0) {

            curl_slist_free_all(headers);
            curl_easy_cleanup(curl);
        }
    }
    return processHttpResult();
}

std::string Widget::getRequestUrl(const QString &requestType, const QStringList &prama){
    std::string url = "http://210.45.250.5:8080/BioDesigner/core.jsp?requestType=" + requestType.toStdString();
    if (requestType == "recommendation"){
        url += "&partNum=" + QVariant(prama.size()).toString().toStdString();
        for (int i = 0; i < prama.size(); i++){
            url += "&partName" + QVariant(i+1).toString().toStdString() + "=" + prama.at(i).toStdString();
        }
    }else{
        url += "&partName="+prama.at(0).toStdString();
    }

    //qDebug() << QString::fromStdString(url) << endl;
    return url;
}



QStringList Widget::processHttpResult(){
    QRegExp reg("<p>(.*)<\/p>");
    QStringList results;
    QString result = QString::fromStdString(resultString);
    qDebug() << result << endl;
    result = result.trimmed();
    result = result.replace(QRegExp("[\t\v\s\r\n]*"), QString(""));
    //qDebug() << result << endl;
    int pos = 0;
    //qDebug() << 1 << endl;
    //qDebug() << reg.indexIn(result, 0) << endl;
    QString biobrick;
    while((pos = reg.indexIn(result, pos))!= -1)
    {
        reg.cap(0);
        biobrick += reg.cap(1);
        biobrick.replace(QString(" "), QString("|"));
        pos += reg.matchedLength();
    }
    //qDebug() << biobrick.split("||</p>||||<p>||");
    return biobrick.split("||</p>||||<p>||");
}
*/
