package ru.yappy.rzdengineerassistant.employeedataextractor.server.service;

import org.mapstruct.Mapper;
import ru.yappy.rzdengineerassistant.employeedataextractor.dtos.AsutrEmployeeDto;
import ru.yappy.rzdengineerassistant.employeedataextractor.server.model.AsutrEmployeeEntity;

/**
 * Интерфейс-мэппер для сущностей сотрудников ОАО "РЖД", на основании которого Mapstruct создает реализацию.
 */
@Mapper(componentModel = "spring")
public interface AsutrEmployeeMapper {

    /**
     * Мэппит массив entity-объектов сущностей работников компании в массив DTO-объектов.
     *
     * @param entities массив entity-объектов сущностей работников компании
     * @return массив DTO-объектов, содержащих информацию о работниках
     */
    AsutrEmployeeDto[] toDtoArray(AsutrEmployeeEntity[] entities);

}