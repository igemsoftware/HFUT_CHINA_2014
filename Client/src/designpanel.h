/*!
 * \file designpanel.h
 * \brief the design panel class, a customed QListWidget
 *
 * \author Bowen
 * \version 1.0
 * \date 2014.09.30
 */


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

/// \brief the DesignPanel class, customed QListWidget
///
/// the design panel is where the user do the design
class DesignPanel : public QListWidget
{
    Q_OBJECT
public:
    /// \brief construction function
    /// \param parent the parent widget
    explicit DesignPanel(QWidget *parent = 0);
    /// \brief get all the biobricks
    /// \return all the biobricks in the design panel
    const QStringList& getBioBrickNames() const;
    /// \brief insert a BioBrick in certain position
    /// \param index position to insert
    /// \param name informaiton for the biobrick to be inserted
    void insertBioBrick(int index, const QString& name);
    /// \brief clean the design panel
    void clean();
    /// \brief set the reommend biobricks
    /// \param biobricks the recommend biobrick list
    void setRecommendBioBrick(QStringList biobricks);

    /// \brief get the design result image
    /// \param function the function about the design
    /// \return the result image
    QImage *getResultImage(const QString &function);
    /// \brief repaint the panel
    void rePaintPanel();
signals:
    void relevantActived(QStringList*);
    void infoActived(QString);
    void biobrickadded();
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

    /// \brief perform the drag action
    void performDrag();

    /// \brief show the recommend BioBricks
    void showRecommend();

    /// \brief clear the recommend BioBricks
    /// \param isClear whether clean the chosen BioBrick or not
    void clearRecommend(bool isClear=true);

    /// \brief add a BioBrick to the panel
    /// \param biobrick the information for a BioBrick
    void addBioBrick(QString biobrick);

    /// \brief paint the background image
    void paintBackground();

    /// \brief get recommendation
    void performRecommend();


};

#endif // DESIGNPANEL_H
