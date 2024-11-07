package ru.yappy.rzdengineerassistant.employeedataextractor.server.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yappy.rzdengineerassistant.employeedataextractor.dtos.AsutrEmployeeDto;
import ru.yappy.rzdengineerassistant.employeedataextractor.server.model.AsutrEmployeeEntity;
import ru.yappy.rzdengineerassistant.employeedataextractor.server.out.repo.AsutrEmployeeRepository;
import ru.yappy.rzdengineerassistant.employeedataextractor.server.service.*;

import java.util.List;

/**
 * Реализация сервиса для получения информации о сотрудниках компании ОАО "РЖД" из базы данных приложения АСУТР.
 */
@Slf4j
@Service
public class AsutrEmployeeServiceImpl implements AsutrEmployeeService {
    /** SpringJpa-репозиторий, извлекающий из базы данных АСУТР объекты, касающиеся сущностей сотрудников компании. */
    private final AsutrEmployeeRepository repository;
    /** Мэппер, преобразующий Entity-объекты с информацией о сотрудниках в DTO. */
    private final AsutrEmployeeMapper mapper;

    /**
     * Конструктор объекта сервиса, получающий зависимости из Spring-контейнера.
     *
     * @param repository репозиторий для получения информации о сотрудниках
     * @param mapper мэппер, преобразующий Entity-объекты с информацией о сотрудниках в DTO
     */
    @Autowired
    public AsutrEmployeeServiceImpl(AsutrEmployeeRepository repository,
                                    AsutrEmployeeMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Получает массив объектов с необходимой информацией о сотрудниках компании по их табельным номерам.
     *
     * @param personnelNumbers список табельных номеров сотрудников
     * @return массив объектов с информацией о сотрудниках
     */
    @Override
    public AsutrEmployeeDto[] getAsutrEmployeeDtosByPersonnelNumbers(List<Long> personnelNumbers) {
        log.debug("Начало выполнения операции получения информации о сотрудниках с табельными номерами {}",
                personnelNumbers);
        AsutrEmployeeEntity[] employeeEntities =
                repository.findAllByAsutrPersonnelNumberIn(personnelNumbers);
        AsutrEmployeeDto[] employeeDtos = mapper.toDtoArray(employeeEntities);
        log.debug("Информация о {} сотрудниках получена из базы данных и преобразована в DTO", employeeDtos.length);
        return employeeDtos;
    }

}