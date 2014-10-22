HFUT_CHINA_2014
===============
# BioFunctional_Desinger Client

###Windows User

Just unpackage the client package, and run the ext file

###Mac user

Open the dmg file, copy the app to the Application folder, and run it.

# BioFunctional_Designer Server

---

## Softwares Preparation
    
Software preparation for the deployment on server.
    
### Jdk & Jre

The server of BioDesigner is written in JAVA and JSP, and the JSP page will be parsed into servlet. So, first of all, you need to establish the java environment on the server.


#### ----Windows Server

If you use a server with windows edition, you can download the jdk below.

[Download Jdk&Jre Here](http://www.oracle.com/technetwork/java/index.html)


After you install java, you need to add the path of jdk and jre to system:
Add the following records to PATH:
    \%JAVA_HOME%\jdk\bin;
Add the following data to CLASSPATH:
    .;\%JAVA_HOME%\jre\lib;

    
#### ----linux(Ubuntu and Debian Edition)
    
Conduct the commands below to install openjdk:

    sudo apt-get update

    sudo apt-get install openjdk-7-jdk

After install the openjdk, you need to add the following records to /etc/profile data.

    export JAVA_HOME=/usr/local/jdk1.8.0/
    
    export JRE_HOME=/usr/local/jdk1.8.0/jre/
    
    export PATH=$JAVA_HOME/bin:$JAVA_HOME/jre/bin:$PATH
    
    export CLASSPATH=$CLASSPATH:.:$JAVA_HOME/lib:$JAVA_HOME/jre/lib
    
---
### Apache Tomcat
apache tomcat is a jsp server, and the jsp files will be parsed into servlet files, in order to response http request.

#### ----Windows

You can download apache tomcat at the website below, and tomcat-7 will be a better choice.

[download tomcat here](http//tomcat.apache.org)

When you install tomcat, the program may ask you to provide your jdk&jre path.parsing

#### ----Linux(Ubuntu and Debian Edition)

Conduct the following commands in your terminal:

    sudo apt-get update
    
    sudo apt-get install tomcat7

---

### MySQL Server

Our project need a database service. There are many kinds of databases in the market. And we use Oracle MySQL database, because it's very powerful and very light. By the way, it's free of charge.

#### ----Windows

You can download MySQL server at the website below. As for a better performination, we suggest that you choose MySQL 5.x edition.

[download MySQL here](http://www.mysql.com)



#### ----Linux (Ubuntu and Debian Edition)
Conduct the following commands in your terminal:

    sudo apt-get update
    
    sudo apt-get install mysql-server
    
---
## Deployment

The deployment process is consisted of 2 steps operation.First of all, you need to push all the jsp and java codes to tomcat server, and the second is to create the database.

### ----server

Fortunatly, MyEclipse has compiled all the java codes to class files. So, you will first create a folder in the '%TOMCAT_HOME%\webapp' path, and then, you can copy all the files to your own folder which is created at the first step. At last, you could easily start this project by start your tomcat server.

### ----database

We have create an sql file named source.sql, all the data in our database is included. So, after you log in, you create your own database named as you like(we use BioDesiger as our name). If you create a different name, you must modify the hibernate.cfg.xml file. Set the value of the key "hibernate.connection.url" with
    
    jdbc:mysql://localhost/Your db name

Also, you may need to update the value of the keys "hibernate.connection.username" and "hibernate.connection.password"

After that, you can Insert all the data to your database. You can use "source" command to complete it quickly.So, please change directory to the source.sql file path, and then, log in to your database and conduct the command below:

    source source.sql
   

---
## api

We provide two kinds of methods to connect to our server. The first method is using our software, which is written with QT. And the second method is using your browser.You can quickly get all the infomation by simply send several parameters to our server. We provide some examples to you.
All the infomation the server response is JSON format data. You can parse it with javacript and so on.

    propertys:
        requestType: partRequest  twinsRequesst deviceRequest recentlyUsed recommendation ambiguous
    
        partNum: 0~4;
        
        partName1~4: all the partName;
        
        type:all the part type;
        
        userInput:any thing input
        
        


1.http://210.45.250.5:8080/BioDesigner/core.jsp?requestType=partRequest&partName=BBa_R0011

response:
{"root":[{"id":2998,"recentlyUsedPart":null,"partName":"BBa_R0011","sequence":"","device":null,"partID":186,"partUrl":"http://parts.igem.org/Part:BBa_R0011","type":"Regulatory"}]}


2.http://210.45.250.5:8080/BioDesigner/core.jsp?requestType=recentlyUsed

response：
{"root":["10.16.25.234"]}

3.http://210.45.250.5:8080/BioDesigner/core.jsp?requestType=twinsRequest&basePart=BBa_Y00009

response:
{"root":[{"id":6533,"recentlyUsedPart":null,"partName":"BBa_J63001","sequence":"","device":null,"partID":7494,"partUrl":"http://parts.igem.org/Part:BBa_J63001","type":"Reporter"},{"id":5793,"recentlyUsedPart":null,"partName":"BBa_K105008","sequence":"","device":null,"partID":12016,"partUrl":"http://parts.igem.org/Part:BBa_K105008","type":"Coding"},{"id":1609,"recentlyUsedPart":null,"partName":"BBa_K165005","sequence":"","device":null,"partID":12636,"partUrl":"http://parts.igem.org/Part:BBa_K165005","type":"Coding"}]}


4.http://210.45.250.5:8080/BioDesigner/core.jsp?requestType=ambiguous&partNum=2&partName1=BBa_R0011&partName2=BBa_B0012&userInput=BBa_K10

reponse:
{"root":[{"id":28,"recentlyUsedPart":null,"partName":"BBa_K1019001","sequence":"","device":null,"partID":30373,"partUrl":"http://parts.igem.org/Part:BBa_K1019001","type":"Coding"},{"id":77,"recentlyUsedPart":null,"partName":"BBa_K1092105","sequence":"","device":null,"partID":30773,"partUrl":"http://parts.igem.org/Part:BBa_K1092105","type":"Protein_Domain"},{"id":96,"recentlyUsedPart":null,"partName":"BBa_K108011","sequence":"","device":null,"partID":12421,"partUrl":"http://parts.igem.org/Part:BBa_K108011","type":"Coding"},{"id":146,"recentlyUsedPart":null,"partName":"BBa_K1092000","sequence":"","device":null,"partID":29551,"partUrl":"http://parts.igem.org/Part:BBa_K1092000","type":"Coding"},{"id":237,"recentlyUsedPart":null,"partName":"BBa_K105027","sequence":"","device":null,"partID":12726,"partUrl":"http://parts.igem.org/Part:BBa_K105027","type":"Regulatory"},{"id":244,"recentlyUsedPart":null,"partName":"BBa_K105028","sequence":"","device":null,"partID":12738,"partUrl":"http://parts.igem.org/Part:BBa_K105028","type":"Regulatory"},{"id":266,"recentlyUsedPart":null,"partName":"BBa_K1051401","sequence":"","device":null,"partID":28632,"partUrl":"http://parts.igem.org/Part:BBa_K1051401","type":"Regulatory"},{"id":292,"recentlyUsedPart":null,"partName":"BBa_K1051702","sequence":"","device":null,"partID":28560,"partUrl":"http://parts.igem.org/Part:BBa_K1051702","type":"DNA"},{"id":350,"recentlyUsedPart":null,"partName":"BBa_K1039001","sequence":"","device":null,"partID":28595,"partUrl":"http://parts.igem.org/Part:BBa_K1039001","type":"DNA"},{"id":352,"recentlyUsedPart":null,"partName":"BBa_K102950","sequence":"","device":null,"partID":12788,"partUrl":"http://parts.igem.org/Part:BBa_K102950","type":"Regulatory"}]}

5.http://210.45.250.5:8080/BioDesigner/core.jsp?requestType=ambiguous&partNum=2&partName1=BBa_R0011&partName2=BBa_B0012&userInput=Terminator

response:
{"root":[{"id":726,"recentlyUsedPart":null,"partName":"BBa_B0011","sequence":"","device":null,"partID":144,"partUrl":"http://parts.igem.org/Part:BBa_B0011","type":"Terminator"}]}

6.http://210.45.250.5:8080/BioDesigner/core.jsp?requestType=recommendation&partNum=2&partName1=BBa_B0010&partName2=BBa_R0011

response:
{"root":[{"id":3679,"recentlyUsedPart":null,"partName":"BBa_B0034","sequence":"","device":null,"partID":151,"partUrl":"http://parts.igem.org/Part:BBa_B0034","type":"RBS"},{"id":997,"recentlyUsedPart":null,"partName":"BBa_B0032","sequence":"","device":null,"partID":149,"partUrl":"http://parts.igem.org/Part:BBa_B0032","type":"RBS"}]}

---
---

   
    
    