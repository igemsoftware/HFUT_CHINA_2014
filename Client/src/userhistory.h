#ifndef USERHISTORY_H
#define USERHISTORY_H

#include <QString>
#include <QStringList>
#include <QDir>

class UserHistory
{
public:
    UserHistory();

    static void sendUserInput(QString name);
    static void getUserId();
    static QStringList *getUserHistory();

private:

    static void saveUserId();
    static QStringList *processHistoryHttpResult();
};

#endif // USERHISTORY_H
