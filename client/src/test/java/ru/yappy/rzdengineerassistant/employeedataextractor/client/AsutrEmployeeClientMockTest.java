package ru.yappy.rzdengineerassistant.employeedataextractor.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.ResourceAccessException;
import ru.yappy.rzdengineerassistant.commonclasses.exception.ExternalServiceException;
import ru.yappy.rzdengineerassistant.employeedataextractor.client.impl.AsutrEmployeeClientImpl;
import ru.yappy.rzdengineerassistant.employeedataextractor.dtos.*;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withResourceNotFound;

@RestClientTest(value = AsutrEmployeeClient.class,
        properties = { "ede.server.url.prefix=http://localhost:8074" })
@ContextConfiguration(classes = { AsutrEmployeeClientImpl.class })
public class AsutrEmployeeClientMockTest {
    private final AsutrEmployeeClient client;
    private final MockRestServiceServer server;
    private final ObjectMapper objectMapper;

    @Autowired
    public AsutrEmployeeClientMockTest(AsutrEmployeeClient client,
                                                 MockRestServiceServer server,
                                                 ObjectMapper objectMapper) {
        this.client = client;
        this.server = server;
        this.objectMapper = objectMapper;
    }

    @Test
    public void getAsutrDataByPersonnelNumbers_whenGetResponseFromServer_thenReturnCorrectDto() throws Exception {
        AsutrEmployeeDto[] asutrEmployeeDtos = new AsutrEmployeeDto[] { EdeTestDataCreator.createAsutrEmployeeDto() };
        server.expect(requestTo("http://localhost:8074/api/v1/employees?emplNumbers=111%2C222%2C333"))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header(HttpHeaders.ACCEPT, "application/json"))
                .andRespond(withSuccess(objectMapper.writeValueAsString(asutrEmployeeDtos),
                        MediaType.APPLICATION_JSON));

        AsutrEmployeeDto[] response = client.getAsutrDataByPersonnelNumbers(List.of(111L, 222L, 333L));
        assertThat(response).isNotNull();
        assertThat(response).isEqualTo(asutrEmployeeDtos);
    }

    @Test
    public void getAsutrDataByPersonnelNumbers_whenGetIOExceptionFromServer_thenThrowResourceAccessException() {
        server.expect(requestTo("http://localhost:8074/api/v1/employees?emplNumbers=111%2C222%2C333"))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header(HttpHeaders.ACCEPT, "application/json"))
                .andRespond(withException(new IOException("Test IOException")));

        ResourceAccessException exception =
                assertThrows(ResourceAccessException.class,
                        () -> client.getAsutrDataByPersonnelNumbers(List.of(111L, 222L, 333L)));
        assertThat(exception.getMessage()).isEqualTo("I/O error on GET request " +
                "for \"http://localhost:8074/api/v1/employees\": Test IOException");
    }

    @Test
    public void getAsutrDataByPersonnelNumbers_whenGetBadRequestResponseFromServer_thenThrowExternalServerException() {
        server.expect(requestTo("http://localhost:8074/api/v1/employees?emplNumbers=111%2C222%2C333"))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header(HttpHeaders.ACCEPT, "application/json"))
                .andRespond(withBadRequest());

        ExternalServiceException exception = assertThrows(ExternalServiceException.class,
                () -> client.getAsutrDataByPersonnelNumbers(List.of(111L, 222L, 333L)));
        assertThat(exception.getMessage()).isEqualTo("При выполнении запроса к внешнему сервису " +
                "'EmployeeDataExtractor' получен ответ с кодом '400' и сообщением: \"\"");
    }

    @Test
    public void getAsutrDataByPersonnelNumbers_whenGetNotFoundResponseFromServer_thenThrowExternalServerException() {
        server.expect(requestTo("http://localhost:8074/api/v1/employees?emplNumbers=111%2C222%2C333"))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header(HttpHeaders.ACCEPT, "application/json"))
                .andRespond(withResourceNotFound());

        ExternalServiceException exception = assertThrows(ExternalServiceException.class,
                () -> client.getAsutrDataByPersonnelNumbers(List.of(111L, 222L, 333L)));
        assertThat(exception.getMessage()).isEqualTo("При выполнении запроса к внешнему сервису " +
                "'EmployeeDataExtractor' получен ответ с кодом '404' и сообщением: \"\"");
    }

    @Test
    public void getAsutrDataByPersonnelNumbers_whenGet200WithoutBody_thenThrowIllegalStateException() {
        server.expect(requestTo("http://localhost:8074/api/v1/employees?emplNumbers=111%2C222%2C333"))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header(HttpHeaders.ACCEPT, "application/json"))
                .andRespond(withSuccess());

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> client.getAsutrDataByPersonnelNumbers(List.of(111L, 222L, 333L)));
        assertThat(exception.getMessage()).isEqualTo("При выполнении запроса к сервису " +
                "'EmployeeDataExtractor' для получения необходимой расширенной информации о сотрудниках получен " +
                "ответ с кодом '200 OK', но отсутствующим телом ответа.");
    }

}