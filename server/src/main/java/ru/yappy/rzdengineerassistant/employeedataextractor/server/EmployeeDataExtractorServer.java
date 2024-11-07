package ru.yappy.rzdengineerassistant.employeedataextractor.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Запуск сервера для получения расширенной информации о работниках ОАО "РЖД".
 */
@SpringBootApplication
public class EmployeeDataExtractorServer {

    /**
     * Запускает сервис для получения расширенной информации о работниках ОАО "РЖД".
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        SpringApplication.run(EmployeeDataExtractorServer.class, args);
    }

}
