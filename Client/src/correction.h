/*!
 * \file correction.h
 * \brief The head file for Correction
 *
 * The file define the class Correction
 *
 * \author Bowen
 * \version 1.0
 * \date 2014.09.30
 */


#ifndef CORRECTION_H
#define CORRECTION_H

#include <QStringList>
#include <QString>
#include <QList>

/*!
 * \brief Correction class, which will do the correction
 *
 * the class will send http request and get the result data
 */
class Correction
{
public:
    /// \brief the construction function
    Correction();
    /// \brief check the sequence
    ///
    /// check the sequence's correction, send the sequence to the server and get the result
    ///
    /// \param biobricks the sequence
    /// \return the error biobrick index
    static QList<int> doCorrection(QStringList biobricks);

private:
    /// \brief process the http response, json
    /// \return the index for the error
    static QList<int> processHttpResult();
    /// \brief get the request url
    /// \param requestType the request type int the url, CheckRequest in this case
    /// \param prama the biobrick sequence
    /// \return the url
    static std::string getRequestUrl(const QString requestType, const QStringList prama);

    /// \brief send the request and get the json data, call the analyse function and return the result
    /// \param requestType the request type int the url, CheckRequest in this case
    /// \param prama the biobrick sequence
    /// \return the error index
    static QList<int> getReslut(const QString requestType, const QStringList prama);
    
};

#endif // CORRECTION_H
