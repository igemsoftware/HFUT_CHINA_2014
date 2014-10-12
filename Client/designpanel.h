#ifndef DESIGNPANEL_H
#define DESIGNPANEL_H

#include <QListWidget>
#include <QStringList>
#include <QString>
#include <QDragEnterEvent>
#include <QDragMoveEvent>
#include <QDropEvent>
#include <QMouseEvent>
#include <QKeyEvent>
#include <QDragLeaveEvent>
#include <QPoint>
#include <QImage>


class DesignPanel : public QListWidget
{
    Q_OBJECT
public:
    explicit DesignPanel(QWidget *parent = 0);
    const QStringList& getBioBrickNames() const;
    void insertBioBrick(int index, const QString& name);
    void clean();
    void setRecommendBioBrick(QStringList biobricks);

    QImage *getResultImage(const QString &function);
    void rePaintPanel();
signals:
    void relevantActived(QStringList*);
    void infoActived(QString);
public slots:

protected:
    void dragEnterEvent(QDragEnterEvent *event);
    void dragMoveEvent(QDragMoveEvent *e);
    //void dragLeaveEvent(QDragLeaveEvent *e);
    void dropEvent(QDropEvent *event);
    void mouseMoveEvent(QMouseEvent *e);
    void mousePressEvent(QMouseEvent* event);
    void keyPressEvent(QKeyEvent *event);

private:
    QStringList m_biobrickNames;
    QStringList m_recommendBioBrickNames;
    QPoint m_mouseStartPos;

    QListWidgetItem* m_recommendItem;

    int m_chosenBioBrick;
    void performDrag();
    void showRecommend();
    void clearRecommend();
    void addBioBrick(QString biobrick);

    void paintBackground();

    void performRecommend();
    QStringList processHttpResult();
    std::string getRequestUrl(const QString& requestType, const QStringList& prama);

    QStringList getReslutList(const QString& requestType, const QStringList& prama);
};

#endif // DESIGNPANEL_H
