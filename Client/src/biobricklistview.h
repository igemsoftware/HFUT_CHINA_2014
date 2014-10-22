/*!
 * \file biobricklistview.h
 * \brief The head file for BioBrickListView
 *
 * The file define the class BioBrickListView
 *
 * \author Bowen
 * \version 1.0
 * \date 2014.09.30
 */

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

/*!
 * \brief BioBrickListView class, which is the container for BioBrick
 * This class is a customed class, inhenirt QListWidget, which can
 * show all the BioBrick in a list. Drag supported
 */
class BioBrickListView : public QListWidget
{
    Q_OBJECT
public:
    /// \brief Construction function
    /// \param parent The parent for this widget
    explicit BioBrickListView(QWidget *parent = 0);

    /// \brief Clean all the BioBrick in the list
    void cleanList();
signals:
    void infoActived(QString);
public slots:
    /// \brief add a BioBrick to the list
    /// \param info The information for a BioBrick
    /// \return no retur value
    void addBioBrick(const QString& info);

protected:
    /// \brief proecss drag enter event
    /// \param event the drag event
    /// \return no return
    void dragEnterEvent(QDragEnterEvent *event);
    /// \brief process drag move event
    /// \param e the drag move event
    /// \return no return
    void dragMoveEvent(QDragMoveEvent *e);
    /// \brief process the drop event
    /// \param event drop event
    /// \return no return
    void dropEvent(QDropEvent *event);
    /// \brief process mouse move event
    /// \param e mouse event
    /// \return no return
    void mouseMoveEvent(QMouseEvent *e);
    /// \brief process the moust press event
    /// \param event mouse event
    /// \return no return
    void mousePressEvent(QMouseEvent* event);
    /// \brief process the key event
    /// \param event key event
    /// \return no return
    void keyPressEvent(QKeyEvent *event);

private:
    QStringList m_biobrickNames;
    QPoint m_mouseStartPositon;

    /// \brief show the drag operation
    void performDrag();
};

#endif // BIOBRICKLISTVIEW_H
