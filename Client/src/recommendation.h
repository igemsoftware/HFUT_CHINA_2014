/*!
 * \file recommendation.h
 * \brief the recommendation class, which will do the recommendation
 *
 * \author Bowen
 * \version 1.0
 * \date 2014.09.30
 */


#ifndef RECOMMENDATION_H
#define RECOMMENDATION_H

//#include <vector>
#include <QStringList>

/// \brief the recommendation class
///
/// the class will send http request and get the recommend result
class Recommendation
{
public:
    Recommendation();
    /// \brief do the recommendation
    /// \param biobricks the current sequence
    /// \return the recommended BioBricks
    static QStringList* doRecommendation(QStringList& biobricks);
    /// \brief get the relevant BioBricks based on one selected BioBrick
    /// \param name the name for a BioBrick
    /// \return the relevant BioBricks
    static QStringList* getRelevantBioBricks(const QString &name);
private:
    /// \brief do the http request and get the json data
    /// \return the result list(BioBircks)
    static QStringList *processHttpResult();
    /// \brief form the request url
    /// \param requestType the requested result type, recommendation or twinsRequest
    /// \param prama the BioBricks
    /// \return the url string
    static std::string getRequestUrl(const QString& requestType, const QStringList& prama);

    /// \brief analyse the json data and get the BioBrick list
    /// \param requestType the requested result type, recommendation or twinsRequest
    /// \param prama the BioBricks
    /// \return the BioBricks list
    static QStringList* getReslutList(const QString& requestType, const QStringList& prama);
};

#endif // RECOMMENDATION_H
