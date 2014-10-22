#include "widget.h"
#include <QApplication>
#include <QFile>
#include <QTextStream>

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    QFile stylesheetFile(":/qss/biodesigner.css");
    QString stylesheetStr;
    if (stylesheetFile.open(QIODevice::ReadOnly | QIODevice::Text)){
        QTextStream in(&stylesheetFile);
        stylesheetStr = in.readAll();
        stylesheetFile.close();
    }
    a.setStyleSheet(stylesheetStr);
    Widget w;
    w.show();

    return a.exec();
}
