package ru.yappy.rzdengineerassistant.employeedataextractor.server.out.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yappy.rzdengineerassistant.employeedataextractor.server.model.AsutrEmployeeEntity;

import java.util.List;

/**
 * SpringJpa-репозиторий, извлекающий из базы данных АСУТР объекты, касающиеся сущностей сотрудников компании.
 */
@Repository
public interface AsutrEmployeeRepository extends JpaRepository<AsutrEmployeeEntity, Long> {

    /**
     * Запросный метод, выполняющий поиск необходимой информации о сотрудниках компании по их табельным номерам.
     *
     * @param personnelNumbers список табельных номеров сотрудников
     * @return массив объектов с информацией о сотрудниках
     */
    AsutrEmployeeEntity[] findAllByAsutrPersonnelNumberIn(List<Long> personnelNumbers);

}