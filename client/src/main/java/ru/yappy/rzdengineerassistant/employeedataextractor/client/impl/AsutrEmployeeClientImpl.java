package ru.yappy.rzdengineerassistant.employeedataextractor.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.yappy.rzdengineerassistant.commonclasses.exception.ExternalServiceException;
import ru.yappy.rzdengineerassistant.employeedataextractor.client.AsutrEmployeeClient;
import ru.yappy.rzdengineerassistant.employeedataextractor.dtos.AsutrEmployeeDto;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс-реализация клиента для получения расширенной информации о сотрудниках ОАО "РЖД" из базы данных приложения АСУТР.
 */
@Service
public class AsutrEmployeeClientImpl implements AsutrEmployeeClient {
    /** Spring-клиент, используемый для выполнения HTTP-запросов. */
    private final RestTemplate restTemplate;
    /** Константа, содержащая часть URL-адреса, общую для всех методов-запросов данного клиента. */
    private static final String API_PREFIX = "/api/v1/employees";

    /**
     * Конструктор объекта-клиента, получающий зависимости из Spring-контейнера.
     *
     * @param serverUrlPrefix префикс URL-адреса сервера, значение содержится в application.properties
     * @param restTemplateBuilder объект-билдер Spring-клиента для выполнения HTTP-запросов
     */
    @Autowired
    public AsutrEmployeeClientImpl(@Value("${ede.server.url.prefix}") String serverUrlPrefix,
                                   RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrlPrefix + API_PREFIX))
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                .build();
    }

    /**
     * Получает расширенную информацию о сотрудниках из базы данных приложения АСУТР.
     *
     * @param personnelNumbers табельные номера сотрудников - идентификаторы в базе данных приложения АСУТР
     * @return массив DTO с расширенной информацией о сотрудниках
     */
    @Override
    public AsutrEmployeeDto[] getAsutrDataByPersonnelNumbers(List<Long> personnelNumbers) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        ResponseEntity<AsutrEmployeeDto[]> responseEntity;
        try {
            responseEntity = restTemplate.exchange(
                    "?emplNumbers={emplNumbers}",
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    AsutrEmployeeDto[].class,
                    personnelNumbers.stream().map(String::valueOf).collect(Collectors.joining(","))
            );
        } catch (HttpStatusCodeException exception) {
            throw new ExternalServiceException("EmployeeDataExtractor", exception.getStatusCode().value(),
                    exception.getResponseBodyAsString());
        }
        if (!responseEntity.hasBody()) {
            throw new IllegalStateException("При выполнении запроса к сервису 'EmployeeDataExtractor' для получения " +
                    "необходимой расширенной информации о сотрудниках получен ответ с кодом '" +
                    responseEntity.getStatusCode() + "', но отсутствующим телом ответа.");
        }
        return responseEntity.getBody();
    }

}