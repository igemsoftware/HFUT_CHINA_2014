#ifndef SEARCH_H
#define SEARCH_H

#include <QStringList>

class Search
{
public:
    Search();

    static QStringList* searchBioBrick(const QString& keywords, const QStringList &userInput);

private:
    static QStringList *processHttpResult();
    static std::string getRequestUrl(const QString& requestType, const QStringList& prama,
                                     const QString& keyword);

    static QStringList* getReslutList(const QString& requestType, const QStringList& prama,
                                      const QString& keyword);
};

#endif // SEARCH_H
