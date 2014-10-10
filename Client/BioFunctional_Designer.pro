#-------------------------------------------------
#
# Project created by QtCreator 2014-09-20T19:09:07
#
#-------------------------------------------------

QT       += core gui designer network

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = BioFunctional_Designer
TEMPLATE = app


SOURCES += main.cpp\
        widget.cpp \
    recommendation.cpp \
    correction.cpp \
    biobricklistview.cpp \
    designpanel.cpp \
    search.cpp

HEADERS  += widget.h \
    recommendation.h \
    correction.h \
    biobricklistview.h \
    designpanel.h \
    search.h

FORMS += \
    form.ui

RESOURCES += \
    resources.qrc

LIBS += -L /usr/local/lib -lcurl
INCLUDEPATH += -I /usr/local/Cellar/boost/1.55.0_2/include
