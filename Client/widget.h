#ifndef WIDGET_H
#define WIDGET_H

/**
* @author Bowen Gong
**/

#include <QWidget>


#include "correction.h"
#include "recommendation.h"
#include "ui_form.h"

#include <QKeyEvent>
#include <QMouseEvent>
#include <QString>
#include <QFile>

class Widget : public QWidget, public Ui::Form
{
    Q_OBJECT

public:
    Widget(QWidget *parent = 0);

    const QString& getFunctionString() const {return ui->functionInput->text(); }
    ~Widget();
public slots:
    void checkButtonClicked();
    void saveButtonClicked();
    void exportButtonClicked();
    void searchBoxTyped(QString text);
    void newButtonClicked();
    void openButtonClicked();
    void showRelevantBioBricks(QStringList* biobricks);
    void showBioBrickInfo(QString info);

protected:
    void keyPressEvent(QKeyEvent *e);

private:
    Ui::Form* ui;
    QString m_projectName;
    QString m_filePath;
    QFile* m_projectFile;

    void saveProject();
    void exportResult();
    void readProject();
    void cleanWidget();

    void performSearch();
    /*
    QStringList processHttpResult();
    std::string getRequestUrl(const QString& requestType, const QStringList& prama);

    QStringList getReslutList(const QString& requestType, const QStringList& prama);
    */
};

#endif // WIDGET_H
