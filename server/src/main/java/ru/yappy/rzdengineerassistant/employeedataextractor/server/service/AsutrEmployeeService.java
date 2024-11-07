package ru.yappy.rzdengineerassistant.employeedataextractor.server.service;

import ru.yappy.rzdengineerassistant.employeedataextractor.dtos.AsutrEmployeeDto;

import java.util.List;

/**
 * Интерфейс, определяющий тип и поведение всех объектов-сервисов, работающих с сущностями сотрудников компании
 * ОАО "РЖД", содержащимися в базе данных приложения АСУТР.
 */
public interface AsutrEmployeeService {

    /**
     * Получает массив объектов с необходимой информацией о сотрудниках компании по их табельным номерам.
     *
     * @param personnelNumbers список табельных номеров сотрудников
     * @return массив объектов с информацией о сотрудниках
     */
    AsutrEmployeeDto[] getAsutrEmployeeDtosByPersonnelNumbers(List<Long> personnelNumbers);

}