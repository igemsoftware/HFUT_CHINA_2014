#include "userhistory.h"
#include <curl/curl.h>
#include <boost/property_tree/ptree.hpp>
#include <boost/property_tree/json_parser.hpp>
#include <boost/foreach.hpp>
#include <string>
#include <QDebug>
#include <QSettings>
#include <QDateTime>
#include <QHostInfo>
#include <QMessageBox>

UserHistory::UserHistory()
{
}
std::string uresultStr = "";
size_t uprocessHTTPRes(void* buffer, size_t size, size_t nmenb, void* /*stream*/){
    uresultStr += (char*)buffer;


    return size* nmenb;
}
void UserHistory::sendUserInput(QString name){
    QSettings settings("ideaworld", "Application BioFunctional_Designer");
    if (settings.value("uid", -1).toInt() == -1){
        //QMessageBox::warning(0, "error", "error"+settings.fileName());
        getUserId();
    }
    //QMessageBox::warning(0, "error2", "error2"+settings.fileName());
    //qDebug() << settings.fileName() << endl;
    std::string uid = settings.value("uid").toString().toStdString();
    std::string url = "http://210.45.250.5:8080/BioDesigner/core.jsp?requestType=record&partName=" +
            name.toStdString() + "&userInfo=" + uid;

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
        //curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, &processHTTPRes);
        res = curl_easy_perform(curl);
        if (res != 0) {
            curl_slist_free_all(headers);
            curl_easy_cleanup(curl);
        }
    }
}

void UserHistory::getUserId(){
    QString userInfo = QHostInfo::localHostName();
    //qDebug() << userInfo << endl;
    std::string url = "http://210.45.250.5:8080/BioDesigner/core.jsp?requestType=regRequest&userInfo=" +
            userInfo.toStdString();
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
        curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, &uprocessHTTPRes);
        res = curl_easy_perform(curl);
        if (res != 0) {
            curl_slist_free_all(headers);
            curl_easy_cleanup(curl);
        }
    }
    saveUserId();
}

void UserHistory::saveUserId(){
    using namespace boost::property_tree;
    using std::string;
    uresultStr = uresultStr.substr(0, uresultStr.find_first_of("<"));

    //qDebug() << QString::fromStdString(uresultStr) << endl;
    std::stringstream jsoncontent(uresultStr);
    ptree resultTree;

    try{
        read_json(jsoncontent, resultTree);
    } catch(ptree_error & error){
        qDebug() << "e2" << endl;
        return;
    }
    std::string uid = resultTree.get_child("root").begin()->second.get<string>("userInfo");
    uresultStr.clear();
    QSettings settings("ideaworld", "Application BioFunctional_Designer");
    //qDebug() << QString::fromStdString(uid) << endl;
    settings.setValue("uid", QString::fromStdString(uid));
}

QStringList* UserHistory::getUserHistory(){
    QSettings settings("ideaworld", "Application BioFunctional_Designer");
    if (settings.value("uid", -1).toInt() == -1){
        getUserId();
        return NULL;
    }
    QString uid = settings.value("uid").toString();
    std::string url = "http://210.45.250.5:8080/BioDesigner/core.jsp?requestType=recentlyUsed&userInfo="
            + uid.toStdString();
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
        curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, &uprocessHTTPRes);
        res = curl_easy_perform(curl);
        if (res != 0) {
            curl_slist_free_all(headers);
            curl_easy_cleanup(curl);
        }
    }
    return processHistoryHttpResult();
}

QStringList* UserHistory::processHistoryHttpResult(){
    using namespace boost::property_tree;
    using std::string;
    uresultStr = uresultStr.substr(0, uresultStr.find_first_of("<"));
    //qDebug() << QString::fromStdString(resultStr) << endl;
    std::stringstream jsoncontent(uresultStr);
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
        uresultStr.clear();
        qDebug() << *results << endl;
        return results;
    }
    catch(ptree_error& error){
        qDebug() << "e1" << endl;
        return NULL;
    }
}
