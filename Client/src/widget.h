/*! \file widget.h
 * \brief define the Widget class
 *
 * \author Bowen
 * \version 1.0
 * \date 2014.09.30
 */

#ifndef WIDGET_H
#define WIDGET_H

#include <QWidget>
#include <QTcpSocket>


#include "correction.h"
#include "recommendation.h"
#include "ui_form.h"

#include <QKeyEvent>
#include <QMouseEvent>
#include <QString>
#include <QFile>

/// \brief the widget class, inherit from QWidget, the main widget
///
/// this class is the main widget for this application
class Widget : public QWidget, public Ui::Form
{
    Q_OBJECT

public:
    Widget(QWidget *parent = 0);

    ~Widget();
public slots:

    /// \brief process save button event
    void saveButtonClicked();
    /// \brief process export button event
    void exportButtonClicked();
    /// \brief process seach inout change event
    /// \param text user's input
    void searchBoxTyped(QString text);
    /// \brief  process create new project button event
    void newButtonClicked();
    /// \brief process open button event
    void openButtonClicked();
    /// \brief show the relevant biobricks
    /// \param biobricks the revelant biobrick lisr
    void showRelevantBioBricks(QStringList* biobricks);
    /// \brief show a selected BioBrick
    /// \param info the biobrick's info
    void showBioBrickInfo(QString info);

    void serverConnected();

    void serverDisconnected();

    void showUserHistory();

protected:
    void keyPressEvent(QKeyEvent *e);

private:
    Ui::Form* ui;
    QString m_projectName;
    QString m_filePath;
    QFile* m_projectFile;
    bool m_isCheckable;
    QTcpSocket* client;

    /// \brief save the project
    void saveProject();
    /// \brief export the result as a image
    void exportResult();
    /// \brief read the project file
    void readProject();
    /// \brief clean the widget
    void cleanWidget();




    /// \brief do the search
    void performSearch();
    /*
    QStringList processHttpResult();
    std::string getRequestUrl(const QString& requestType, const QStringList& prama);

    QStringList getReslutList(const QString& requestType, const QStringList& prama);
    */
};

#endif // WIDGET_H
