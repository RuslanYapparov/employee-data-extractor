package ru.yappy.rzdengineerassistant.employeedataextractor.client;

import ru.yappy.rzdengineerassistant.employeedataextractor.dtos.AsutrEmployeeDto;

import java.util.List;

/**
 * Интерфейс, представляющий тип объекта-клиента, отвечающего за получение расширенной информации о сотрудниках компании
 * ОАО "РЖД" из базы данных приложения АСУТР.
 */
public interface AsutrEmployeeClient {

    /**
     * Получает расширенную информацию о сотрудниках из базы данных приложения АСУТР.
     *
     * @param personnelNumbers табельные номера сотрудников - идентификаторы в базе данных приложения АСУТР
     * @return массив DTO с расширенной информацией о сотрудниках
     */
    AsutrEmployeeDto[] getAsutrDataByPersonnelNumbers(List<Long> personnelNumbers);

}