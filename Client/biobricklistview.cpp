#include "biobricklistview.h"
#include "designpanel.h"
#include <QApplication>
#include <QLabel>
#include <QVBoxLayout>
#include <QMimeData>
#include <QDrag>
#include <QPixmap>
#include <QDebug>
#include <QSize>
BioBrickListView::BioBrickListView(QWidget *parent) :
    QListWidget(parent)
{
    setAcceptDrops(false);
    setMouseTracking(true);
    setViewMode(QListView::IconMode);
    setFocusPolicy(Qt::NoFocus);
    setAlternatingRowColors(false);
}

void BioBrickListView::mousePressEvent(QMouseEvent *event){
    if (event->button() == Qt::LeftButton){
        m_mouseStartPositon = event->pos();
        QListWidgetItem* item = this->itemAt(this->mapFrom(this, event->pos()));
        if (item == NULL){
            return;
        }
        //item->setBackgroundColor(QColor(60, 60, 60));
        int index = this->row(item);
        qDebug() << index << endl;
        QStringList infos = m_biobrickNames.at(index).split("|");
        QString info = "<p>Name: " + infos.at(0) + "</p> <p>Type: " + infos.at(1) + "</p> <p>URL: <a href=\" "
                + infos.at(2) + "\">InfoPage</a>";
        emit infoActived(info);
    }
    QListWidget::mousePressEvent(event);
}

void BioBrickListView::mouseMoveEvent(QMouseEvent *e){

    if (e->buttons() & Qt::LeftButton){
        //qDebug() << "mouse move perform" << endl;
        int distance = (e->pos() - m_mouseStartPositon).manhattanLength();
        if (distance >= QApplication::startDragDistance()){
            performDrag();
        }
    }
    QListWidget::mouseMoveEvent(e);
}

void BioBrickListView::performDrag(){
    QListWidgetItem* item = currentItem();
    int index = currentIndex().row();
    if (item){
        QMimeData* mimeData = new QMimeData;
        mimeData->setText(m_biobrickNames.at(index));

        QDrag* drag = new QDrag(this);
        drag->setMimeData(mimeData);
        drag->setPixmap(QPixmap(":/image/brick_icon.png"));
        if (drag->exec(Qt::MoveAction) == Qt::MoveAction){
            m_biobrickNames.removeAt(index);
            delete item;
        }
    }
}

void BioBrickListView::keyPressEvent(QKeyEvent *event){

}

void BioBrickListView::dragEnterEvent(QDragEnterEvent *event){
    DesignPanel* source = qobject_cast<DesignPanel*>(event->source());
    if (source){
        event->setDropAction(Qt::MoveAction);
        event->accept();
    }
}

void BioBrickListView::dragMoveEvent(QDragMoveEvent *e){
    DesignPanel* source = qobject_cast<DesignPanel*>(e->source());
    if (source){
        e->setDropAction(Qt::MoveAction);
        e->accept();
    }
}

void BioBrickListView::dropEvent(QDropEvent *event){
    DesignPanel* source = qobject_cast<DesignPanel*>(event->source());
    if (source){
        addBioBrick(event->mimeData()->text());
        event->setDropAction(Qt::MoveAction);
        event->accept();
    }
}

void BioBrickListView::addBioBrick(const QString &info){
    //qDebug() << "h2" << endl;
    QStringList infos = info.split("|");
    QString tips = "Name: " + infos.at(0) + "\nType: " + infos.at(1) + "\nURL: " + infos.at(2);
    QLabel* label = new QLabel;
    label->setToolTip(tips);
    label->setProperty("biobrick", true);
    QString name = info.split("|").at(0);
    label->setText(name);
    label->setFixedSize(167, 29);
    label->setAlignment(Qt::AlignCenter);
    QWidget* labelContainer = new QWidget();
    QVBoxLayout* layout = new QVBoxLayout();
    layout->addStretch();
    layout->addWidget(label);
    layout->addStretch();
    labelContainer->setLayout(layout);
    QListWidgetItem* item = new QListWidgetItem();
    item->setSizeHint(QSize(177, 40));

    addItem(item);
    setItemWidget(item, labelContainer);
    m_biobrickNames.push_back(info);
}

void BioBrickListView::cleanList(){
    m_biobrickNames.clear();
    clear();
}
