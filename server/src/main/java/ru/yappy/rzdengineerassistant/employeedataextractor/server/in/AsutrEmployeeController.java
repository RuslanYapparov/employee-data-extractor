package ru.yappy.rzdengineerassistant.employeedataextractor.server.in;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yappy.rzdengineerassistant.employeedataextractor.dtos.AsutrEmployeeDto;
import ru.yappy.rzdengineerassistant.employeedataextractor.server.service.AsutrEmployeeService;

import java.util.List;

/**
 * REST-контроллер, обрабатывающий http-запросы на получение информации о сотрудниках компании ОАО "РЖД" из базы данных
 * приложения АСУТР.
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/v1/employees")
public class AsutrEmployeeController {
    /** Сервис для получения информации о сотрудниках компании. */
    private final AsutrEmployeeService service;

    /**
     * Конструктор объекта контроллера, получающий зависимость из Spring-контейнера.
     *
     * @param service сервис для получения информации о сотрудниках компании
     */
    @Autowired
    public AsutrEmployeeController(AsutrEmployeeService service) {
        this.service = service;
    }

    /**
     * Возвращает массив DTO с необходимой информацией о сотрудниках компании ОАО "РЖД" по их табельным номерам.
     *
     * @param asutrPersonnelNumbers список табельных номеров сотрудников
     * @return массив DTO с необходимой информацией о сотрудниках
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public AsutrEmployeeDto[] getEmployeeDataByAsutrPersonnelNumbers(
            @RequestParam(name = "emplNumbers") List<Long> asutrPersonnelNumbers) {
        log.info("Начало обработки http-запроса на получение информации о сотрудниках компании с табельными " +
                "номерами: {}", asutrPersonnelNumbers);
        AsutrEmployeeDto[] asutrEmployeeDtos = service.getAsutrEmployeeDtosByPersonnelNumbers(asutrPersonnelNumbers);
        log.info("Информация о {} сотруднике(-ах) из базы данных АСУТР получена и отправляется клиенту",
                asutrEmployeeDtos.length);
        return asutrEmployeeDtos;
    }

}