module ede.client {

    requires ede.dtos;
    requires spring.beans;
    requires spring.boot;
    requires spring.context;
    requires spring.web;

    requires common.classes;

    exports ru.yappy.rzdengineerassistant.employeedataextractor.client;
    exports ru.yappy.rzdengineerassistant.employeedataextractor.client.impl;

}