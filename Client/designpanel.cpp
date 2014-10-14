#include "designpanel.h"
#include "recommendation.h"
#include "biobricklistview.h"
#include <QApplication>
#include <QMimeData>
#include <QDrag>
#include <QPixmap>
#include <QLabel>
#include <QVBoxLayout>
#include <QDebug>
#include <QPainter>
#include <cstdlib>
#include <QPicture>
#include <QFont>
#include <QRegExp>

#define HOST "210.45.250.5"

#include <curl/curl.h>


DesignPanel::DesignPanel(QWidget *parent):
    QListWidget(parent), m_chosenBioBrick(0)
{
    m_recommendItem = NULL;
    setAcceptDrops(true);
    setSpacing(10);

    setViewMode(QListView::IconMode);
    setWrapping(false);
    setFlow(QListView::LeftToRight);
    setMouseTracking(true);

    setAlternatingRowColors(false);
    //setBackgroundRole(QPalette::Dark);
    //paintBackground();

}

void DesignPanel::paintBackground(){
    QPainter painter(this);
    painter.begin(this);
    painter.setRenderHint(QPainter::Antialiasing);
    painter.drawImage(0, 0, QImage(":image/network-locked-01.png"));
    painter.end();
}

void DesignPanel::mousePressEvent(QMouseEvent* event){
    if (event->button() == Qt::LeftButton){
        m_mouseStartPos = event->pos();
        //qDebug() << event->pos().y() << endl;
        QListWidgetItem* cursorItem = itemAt(event->pos());
        //qDebug() <<mapFrom(this,QCursor::pos()) << endl;
        if (cursorItem && cursorItem->whatsThis() == "recommend"){

            int yDistance = event->pos().y() - 20;
            int index = yDistance/30;
            qDebug () << "press" <<index << endl;
            if (index >= m_recommendBioBrickNames.size()){
                return;
            }
            QStringList infos = m_recommendBioBrickNames.at(index).split("|");
            QString info = "<p>Name: " + infos.at(0) + "</p> <p>Type: " + infos.at(1) + "</p> <p>URL: <a href=\" "
                    + infos.at(2) + "\">InfoPage</a>";

            emit infoActived(info);
            //qDebug() << index << endl;
            addBioBrick(m_recommendBioBrickNames.at(index));
            performRecommend();
        } else if (cursorItem){
            int index = this->row(cursorItem);
            QStringList infos = m_biobrickNames.at(index).split("|");
            QString info = "<p>Name: " + infos.at(0) + "</p> <p>Type: " + infos.at(1) + "</p> <p>URL: <a href=\" "
                    + infos.at(2) + "\">InfoPage</a>";
            //qDebug() << info << endl;
            emit infoActived(info);
            cursorItem->setSelected(true);
        }
    } else if(event->button() == Qt::RightButton){
        int index = this->row(this->itemAt(this->mapFrom(this, event->pos())));
        //qDebug() << index << endl;
        QStringList* relevantBioBricks = Recommendation::getRelevantBioBricks(
                    m_biobrickNames.at(index).split("|").at(0));
        /*
        qDebug() << "geted" << endl;
        for (int i = 0; i < relevantBioBricks->size(); i++){
            qDebug() << relevantBioBricks->at(i) << endl;
        }
        */
        emit relevantActived(relevantBioBricks);
    }
    QListWidget::mousePressEvent(event);
}

void DesignPanel::mouseMoveEvent(QMouseEvent *e){
    if (e->buttons() & Qt::LeftButton){
        int distance = (e->pos() - m_mouseStartPos).manhattanLength();
        if (distance >= QApplication::startDragDistance()){
            performDrag();
        }
    }
    QListWidget::mouseMoveEvent(e);
}

void DesignPanel::performDrag(){
    QListWidgetItem* item = currentItem();
    int index = currentIndex().row();
    if (item){
        QMimeData* mimeData = new QMimeData;
        //qDebug() <<index << endl;
        mimeData->setText(m_biobrickNames.at(index));
        QDrag* drag = new QDrag(this);
        drag->setMimeData(mimeData);
        drag->setPixmap(QPixmap(":/image/brick_icon.png"));
        if (drag->exec(Qt::MoveAction) == Qt::MoveAction){
            m_biobrickNames.removeAt(index);
            this->removeItemWidget(item);
            delete item;
        }
    }
}

void DesignPanel::dragEnterEvent(QDragEnterEvent *event){

    BioBrickListView* source = qobject_cast<BioBrickListView*>(event->source());
    if (source){
        event->setDropAction(Qt::MoveAction);
        event->accept();
    }
}

void DesignPanel::dragMoveEvent(QDragMoveEvent *e){
    if(e->mimeData()->hasFormat("text/plain")){
        e->setDropAction(Qt::MoveAction);
        e->accept();
    }
}

void DesignPanel::dropEvent(QDropEvent *event){
    BioBrickListView* source = qobject_cast<BioBrickListView*>(event->source());
    if (source){
        addBioBrick(event->mimeData()->text());
        event->setDropAction(Qt::MoveAction);
        event->accept();
        //qDebug() << "d1" << endl;
        performRecommend();
        //qDebug() << "d2" << endl;
    }
}
void DesignPanel::clearRecommend(){
    m_chosenBioBrick = 0;
    //qDebug() << this->count() << endl;
    if (m_recommendItem != NULL){
        this->takeItem(this->row(m_recommendItem));
        m_recommendBioBrickNames.clear();
        m_recommendItem = NULL;
    }

}

void DesignPanel::keyPressEvent(QKeyEvent *event){
    switch(event->key()){
    case Qt::Key_D:
    {
        QList<QListWidgetItem*> selects = this->selectedItems();
        if (selects.size() != 0){
            for (int i = 0; i < selects.size(); i++){
                selects.at(i)->setSelected(false);
                int index = this->row(selects.at(i));
                this->takeItem(index);
                m_biobrickNames.removeAt(i);
                clearRecommend();
                performRecommend();
            }
        }
        break;
    }
    case Qt::Key_J:
    {
        //qDebug() << "p1:" <<m_chosenBioBrick << endl;
        m_chosenBioBrick--;
        if (m_chosenBioBrick < 0){
            m_chosenBioBrick = 0;
        }
        //qDebug() <<"p2:"<< m_chosenBioBrick << endl;
        QStringList Jtemp = m_recommendBioBrickNames;
        clearRecommend();
        this->setRecommendBioBrick(Jtemp);
        showRecommend();
        break;
    }
    case Qt::Key_K:
    {
        //qDebug() << "p1:" <<m_chosenBioBrick << endl;
        int recommendCount = m_recommendBioBrickNames.size();
        m_chosenBioBrick++;
        if (m_chosenBioBrick > recommendCount-1){
            m_chosenBioBrick = recommendCount-1;
        }
        //qDebug() <<"p2:"<< m_chosenBioBrick << endl;
        QStringList Ktemp = m_recommendBioBrickNames;
        int tempIndex = m_chosenBioBrick;
        clearRecommend();
        m_chosenBioBrick = tempIndex;
        this->setRecommendBioBrick(Ktemp);
        showRecommend();
        break;
    }
    case Qt::Key_Return:
    {

        addBioBrick(m_recommendBioBrickNames.at(m_chosenBioBrick));
        performRecommend();
        break;
    }
    }
}

const QStringList& DesignPanel::getBioBrickNames() const{
    return m_biobrickNames;
}

void DesignPanel::insertBioBrick(int index, const QString &name){
    m_biobrickNames.insert(index, name);
}

QImage* DesignPanel::getResultImage(const QString& function){
    //qDebug() << function << endl;
    QImage* image = new QImage(QSize(200*m_biobrickNames.size() + 10, 130), QImage::Format_ARGB32);
    QPainter painter(image);
    painter.setRenderHint(QPainter::Antialiasing);
    painter.setBrush(QColor(255, 255, 255));
    painter.drawRect(0, 0, image->width(), image->height());
    int x = 10;
    int y = 20;
    for (int i = 0; i < m_biobrickNames.size(); i++){
        QStringList infos = m_biobrickNames.at(i).split("|");
        QImage image(":image/" + infos.at(1) + ".png");
        if (image.isNull()){
            painter.drawImage(x + i * 190, y, QImage(":/image/dna.png"));
        }else{
            painter.drawImage(x + i * 190, y, image);
        }
        painter.drawText(x + i*167, y + 80, infos.at(0));
        x+= 10;
    }
    painter.setFont(QFont("Helvetica", 20));
    painter.drawText(10, 30, function);
    painter.end();
    return image;
}

void DesignPanel::clean(){
    clear();
    m_biobrickNames.clear();
}

void DesignPanel::rePaintPanel(){
    for (int i = 0; i < m_biobrickNames.size(); i++){
        QStringList infos = m_biobrickNames.at(i).split("|");
        QLabel* label = new QLabel;
        QLabel* textLabel = new QLabel;
        label->setProperty("biobrickImage", true);
        label->setAlignment(Qt::AlignCenter);
        label->setFixedSize(180, 67);
        QPicture dnaPicture();
        QImage image(":image/" + infos.at(1) + ".png");
        if (image.isNull()){
            label->setPixmap(QPixmap::fromImage(QImage(":image/dna.png")));
        }else{
            label->setPixmap(QPixmap::fromImage(image));
        }
        textLabel->setFixedSize(167, 29);
        textLabel->setAlignment(Qt::AlignCenter);
        textLabel->setText(infos.at(0));
        textLabel->setProperty("biobrickName", true);
        QWidget* labelContainer = new QWidget();
        QVBoxLayout* layout = new QVBoxLayout();
        layout->addWidget(label);
        layout->addStretch();
        layout->addWidget(textLabel);
        labelContainer->setLayout(layout);
        QListWidgetItem* item = new QListWidgetItem();
        item->setSizeHint(QSize(185, 120));
        addItem(item);
        setItemWidget(item, labelContainer);
    }
}

void DesignPanel::showRecommend(){
    QListWidgetItem* item = new QListWidgetItem();
    int biobrickCount = m_recommendBioBrickNames.size();
    if (biobrickCount == 0){
        return;
    }
    //qDebug() << m_recommendBioBrickNames << endl;
    //item->setSizeHint(167, 29*biobrickCount);
    QWidget* labelContainer = new QWidget();
    QVBoxLayout* layout = new QVBoxLayout();
    for (int i = 0; i < m_recommendBioBrickNames.size(); i++){
        QLabel* label = new QLabel();
        QStringList infos = m_recommendBioBrickNames.at(i).split("|");
        QString tips = "Name: " + infos.at(0) + "\nType: " + infos.at(1) + "\nURL: " + infos.at(2);
        label->setProperty(i==m_chosenBioBrick ? "chosenReommendBioBrick" : "recommendBioBrick", true);
        label->setToolTip(tips);
        QString name = m_recommendBioBrickNames.at(i).split("|").at(0);
        label->setText(name);
        label->setFixedSize(167, 29);
        label->setAlignment(Qt::AlignCenter);
        layout->addWidget(label);
    }
    labelContainer->setLayout(layout);
    qDebug() << biobrickCount << endl;
    item->setSizeHint(QSize(177, 40*biobrickCount));
    item->setWhatsThis("recommend");
    addItem(item);
    m_recommendItem = item;
    setItemWidget(item, labelContainer);
}


void DesignPanel::performRecommend(){
    //dresultString.clear();
    QStringList parma;
    int index = currentIndex().row();
    int i = m_biobrickNames.size()-1;
    int count = 0;
    while (i >=0 && count <=3){
        parma.push_back(m_biobrickNames.at(i).split("|").at(0));
        i--;
        count++;
    }

    QStringList* resultBioBrick = Recommendation::doRecommendation(parma);
    if (resultBioBrick == NULL){
        return;
    }
    for (int i = 0; i < resultBioBrick->size(); i++){
        m_recommendBioBrickNames.push_back(resultBioBrick->at(i));
    }

    showRecommend();

}

void DesignPanel::setRecommendBioBrick(QStringList biobricks){
    m_recommendBioBrickNames = biobricks;
}

/*
size_t processDesignHTTPRes(void* buffer, size_t size, size_t nmenb, void* stream){
    dresultString += (char*)buffer;
    //qDebug() << QString::fromStdString(resultString) << endl;

    return size* nmenb;
}


QStringList DesignPanel::getReslutList(const QString &requestType, const QStringList &prama){
    std::string url = getRequestUrl(requestType, prama);
    CURL* curl;
    CURLcode res;
    struct curl_slist* headers = NULL;
    headers = curl_slist_append(headers, "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,**;q=0.8");
    headers = curl_slist_append(headers, "Accept-Encoding:	gzip, deflate");
    headers = curl_slist_append(headers, "Accept-Language:	zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
    headers = curl_slist_append(headers, "Connection:	keep-alive");
    headers = curl_slist_append(headers, "User-Agent:	Mozilla/5.0 Gecko/20100101 Firefox/32.0");

    curl = curl_easy_init();
    if (curl){
        curl_easy_setopt(curl, CURLOPT_HTTPHEADER, headers);
        curl_easy_setopt(curl, CURLOPT_URL, url.c_str());
        curl_easy_setopt(curl, CURLOPT_HTTPGET, url.c_str());
        curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, &processDesignHTTPRes);
        res = curl_easy_perform(curl);
        if (res != 0) {

            curl_slist_free_all(headers);
            curl_easy_cleanup(curl);
        }
    }
    return processHttpResult();
}

std::string DesignPanel::getRequestUrl(const QString &requestType, const QStringList &prama){
    std::string url = "http://210.45.250.5:8080/BioDesigner/core.jsp?requestType=" + requestType.toStdString();
    if (requestType == "recommendation"){
        url += "&partNum=" + QVariant(prama.size()).toString().toStdString();
        for (int i = 0; i < prama.size(); i++){
            url += "&partName" + QVariant(i+1).toString().toStdString() + "=" + prama.at(i).toStdString();
        }
    }else{
        url += "&partName="+prama.at(0).toStdString();
    }

    qDebug() << QString::fromStdString(url) << endl;
    return url;
}

QStringList DesignPanel::processHttpResult(){
    QRegExp reg("<p>(.*)<\/p>");
    QStringList results;
    QString result = QString::fromStdString(dresultString);
    //qDebug() << result << endl;
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
void DesignPanel::addBioBrick(QString biobrick){
    clearRecommend();
    std::srand(0);
    QStringList infos = biobrick.split("|");
    QString tips = "Name: " + infos.at(0) + "\nType: " + infos.at(1) + "\nURL: " + infos.at(2);
    QLabel* label = new QLabel;
    QLabel* textLabel = new QLabel;
    label->setToolTip(tips);
    textLabel->setToolTip(tips);
    label->setProperty("biobrickImage", true);
    label->setAlignment(Qt::AlignCenter);
    label->setFixedSize(180, 67);
    QPicture dnaPicture();
    QImage image(":image/" + infos.at(1) + ".png");
    if (image.isNull()){
        label->setPixmap(QPixmap::fromImage(QImage(":image/dna.png")));
    }else{
        label->setPixmap(QPixmap::fromImage(image));
    }
    textLabel->setFixedSize(167, 29);
    textLabel->setAlignment(Qt::AlignCenter);
    qDebug() << biobrick << endl;
    textLabel->setText(infos.at(0));
    textLabel->setProperty("biobrickName", true);
    QWidget* labelContainer = new QWidget();
    QVBoxLayout* layout = new QVBoxLayout();
    layout->addWidget(label);
    layout->addStretch();
    layout->addWidget(textLabel);

    labelContainer->setLayout(layout);
    QListWidgetItem* item = new QListWidgetItem();
    item->setSizeHint(QSize(185, 120));
    addItem(item);
    setItemWidget(item, labelContainer);
    m_biobrickNames.push_back(biobrick);
}

