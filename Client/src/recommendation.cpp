/*!
 * \file recommendation.cpp
 * \brief implement the Recommendation class
 *
 * \author Bowen
 * \version 1.0
 * \date 2014.09.30
 */

#include "recommendation.h"
#include <curl/curl.h>
#include <boost/property_tree/ptree.hpp>
#include <boost/property_tree/json_parser.hpp>
#include <boost/foreach.hpp>
#include <string>
#include <QDebug>

Recommendation::Recommendation()
{
}

QStringList* Recommendation::doRecommendation(QStringList &biobricks){
    return getReslutList("recommendation", biobricks);
}

QStringList* Recommendation::getRelevantBioBricks(const QString &name){
    QStringList prama;
    prama.push_back(name);
    return getReslutList("twinsRequest", prama);
}

std::string dresultStr;
QStringList* Recommendation::processHttpResult(){

    using namespace boost::property_tree;
    using std::string;
    dresultStr = dresultStr.substr(0, dresultStr.find_first_of("<"));
    //qDebug() << QString::fromStdString(resultStr) << endl;
    std::stringstream jsoncontent(dresultStr);
    ptree resultTree;

    try{
        read_json(jsoncontent, resultTree);
    } catch(ptree_error & error){
        qDebug() << "e2" << endl;
        return NULL;
    }

    try{
        ptree biobricks = resultTree.get_child("root");
        QStringList* results = new QStringList();
        //qDebug() << "jp" << endl;
        for (ptree::iterator it = biobricks.begin(); it != biobricks.end(); it++){
            ptree biobrick = it->second;
            string name = biobrick.get<string>("partName");
            string url = biobrick.get<string>("partUrl");
            string type = biobrick.get<string>("type");
            results->push_back(QString::fromStdString(name + "|" + type + "|" + url));
        }
        dresultStr.clear();
        //qDebug() << *results << endl;
        return results;
    }
    catch(ptree_error& error){
        qDebug() << "e1" << endl;

        return NULL;
    }

}

std::string Recommendation::getRequestUrl(const QString &requestType, const QStringList &prama){
    std::string url = "http://210.45.250.5:8080/BioDesigner/core.jsp?requestType="+requestType.toStdString();
    if (requestType == "recommendation"){
        url += "&partNum=" + QVariant(prama.size()).toString().toStdString();
        for (int i = 0; i < prama.size(); i++){
            url += "&partName" + QVariant(i+1).toString().toStdString() + "=" + prama.at(i).toStdString();
        }
    } else if (requestType == "twinsRequest"){
        url += "&basePart=" + prama.at(0).toStdString();
    }
    //qDebug () << QString::fromStdString(url) <<endl;
    return url;
}

size_t dprocessHTTPRes(void* buffer, size_t size, size_t nmenb, void* stream){
    dresultStr += (char*)buffer;
    //qDebug() << QString::fromStdString(resultStr) << endl;

    return size* nmenb;
}

QStringList* Recommendation::getReslutList(const QString &requestType, const QStringList &prama){
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
        curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, &dprocessHTTPRes);
        res = curl_easy_perform(curl);
        if (res != 0) {

            curl_slist_free_all(headers);
            curl_easy_cleanup(curl);
        }
    }

    return processHttpResult();
}

