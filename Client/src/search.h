/*!
 * \file search.h
 * \brief the search class, which will do the search
 *
 * \author Bowen
 * \version 1.0
 * \date 2014.09.30
 */

#ifndef SEARCH_H
#define SEARCH_H

#include <QStringList>

/// \brief the search class
///
/// the search class will do the search action, send http request and get the search result
class Search
{
public:
    Search();

    /// \brief search BioBrick based on keyword
    /// \param keywords the keywords for search
    /// \param userInput the current designed sequence
    /// \return the searched BioBrick list
    static QStringList* searchBioBrick(const QString& keywords, const QStringList &userInput);

private:
    /// \brief do the http request and get the data
    /// \return the searched BioBrick list
    static QStringList *processHttpResult();

    /// \brief form the http request url
    /// \param requestType the http result type, ambiguous in this case
    /// \param prama the params
    /// \param keyword the keyword for searchd
    /// \return the url string
    static std::string getRequestUrl(const QString& requestType, const QStringList& prama,
                                     const QString& keyword);

    /// \brief analyse the json data and get the result list
    /// \param requestType the http result type, ambiguous in this case
    /// \param prama the params
    /// \param keyword the keyword for searchd
    /// \return the result list
    static QStringList* getReslutList(const QString& requestType, const QStringList& prama,
                                      const QString& keyword);
};

#endif // SEARCH_H
