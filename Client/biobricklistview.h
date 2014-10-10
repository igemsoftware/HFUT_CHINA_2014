#ifndef BIOBRICKLISTVIEW_H
#define BIOBRICKLISTVIEW_H

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

class BioBrickListView : public QListWidget
{
    Q_OBJECT
public:
    explicit BioBrickListView(QWidget *parent = 0);
    void cleanList();
signals:
    void infoActived(QString);
public slots:
    void addBioBrick(const QString& info);

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
    QPoint m_mouseStartPositon;

    void performDrag();
};

#endif // BIOBRICKLISTVIEW_H
