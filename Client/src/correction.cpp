/*!
 * \file correction.cpp
 * \brief The implement file for Correction
 *
 * The file implement the class Correction
 *
 * \author Bowen
 * \version 1.0
 * \date 2014.09.30
 */


#include "correction.h"
#include <curl/curl.h>
#include <boost/property_tree/ptree.hpp>
#include <boost/property_tree/json_parser.hpp>
#include <boost/foreach.hpp>
#include <string>
#include <QDebug>

Correction::Correction()
{
}

std::string cresultStr = "";

size_t cprocessHTTPRes(void* buffer, size_t size, size_t nmenb, void* /*stream*/){
    cresultStr += (char*)buffer;
    //qDebug() << QString::fromStdString(resultStr) << endl;

    return size* nmenb;
}

QList<int> Correction::doCorrection(QStringList biobricks){
    return getReslut("checkRequest", biobricks);
}

QList<int> Correction::getReslut(const QString requestType, const QStringList prama){
    std::string url = getRequestUrl(requestType, prama);
    //qDebug() << QString::fromStdString(url) << endl;
    CURL* curl;
    CURLcode res;
    struct curl_slist* headers = NULL;
    headers = curl_slist_append(headers, "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
    headers = curl_slist_append(headers, "Accept-Encoding:	gzip, deflate");
    headers = curl_slist_append(headers, "Accept-Language:	zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
    headers = curl_slist_append(headers, "Connection:	keep-alive");
    headers = curl_slist_append(headers, "User-Agent:	Mozilla/5.0 Gecko/20100101 Firefox/32.0");

    curl = curl_easy_init();

    if (curl){
        curl_easy_setopt(curl, CURLOPT_HTTPHEADER, headers);
        curl_easy_setopt(curl, CURLOPT_URL, url.c_str());
        curl_easy_setopt(curl, CURLOPT_HTTPGET, url.c_str());
        curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, &cprocessHTTPRes);
        res = curl_easy_perform(curl);
        if (res != 0) {

            curl_slist_free_all(headers);
            curl_easy_cleanup(curl);
        }
    }

    return processHttpResult();
}

std::string Correction::getRequestUrl(const QString requestType, const QStringList prama){
    std::string url = "http://210.45.250.5:8080/BioDesigner/core.jsp?requestType="+requestType.toStdString();
    int pramaCount = prama.size();
    if (pramaCount <= 5){
        url += "&partNum=" + QVariant(pramaCount).toString().toStdString();
        for (int i = 0; i < pramaCount; i++){
            url += "&partName" + QVariant(i+1).toString().toStdString() + "=" + prama.at(i).toStdString();
        }
    } else{
        int index = pramaCount - 6;
        for (int i = 0; i < 5; i++, index++){
            url += "&partName" + QVariant(i+1).toString().toStdString() + "=" + prama.at(index).toStdString();
        }
    }
    return url;
}

QList<int> Correction::processHttpResult(){
    using namespace boost::property_tree;
    using std::string;
    cresultStr = cresultStr.substr(0, cresultStr.find_first_of("<"));
    //qDebug() << QString::fromStdString(cresultStr) << endl;
    std::stringstream jsoncontent(cresultStr);
    ptree resultTree;

    try{
        read_json(jsoncontent, resultTree);
    } catch(ptree_error & error){
        qDebug() << "e2" << endl;
        return QList<int>();
    }

    try{
        ptree biobricks = resultTree.get_child("root");
        QList<int> results;
        //qDebug() << "jp" << endl;
        for (ptree::iterator it = biobricks.begin(); it != biobricks.end(); it++){
            ptree biobrick = it->second;
            qDebug() <<"check" <<biobrick.get<int>("index") << endl;
            results.push_back(biobrick.get<int>("index"));
            //qDebug() << "error1" << result << endl;
        }
        cresultStr.clear();
        //qDebug() << result << endl;
        return results;
    }
    catch(ptree_error& error){
        qDebug() << "e1" << endl;

        return QList<int>();
    }

}
