#ifndef RECOMMENDATION_H
#define RECOMMENDATION_H

//#include <vector>
#include <QStringList>

class Recommendation
{
public:
    Recommendation();
    static QStringList* doRecommendation(QStringList& biobricks);
    static QStringList* getRelevantBioBricks(const QString &name);
private:
    static QStringList *processHttpResult();
    static std::string getRequestUrl(const QString& requestType, const QStringList& prama);

    static QStringList* getReslutList(const QString& requestType, const QStringList& prama);
};

#endif // RECOMMENDATION_H
