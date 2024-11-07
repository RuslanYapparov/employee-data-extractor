package ru.yappy.rzdengineerassistant.employeedataextractor.server.in;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yappy.rzdengineerassistant.employeedataextractor.dtos.*;
import ru.yappy.rzdengineerassistant.employeedataextractor.server.service.AsutrEmployeeService;

import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = { EdeExceptionHandler.class, AsutrEmployeeController.class })
public class EdeExceptionHandlerMockTest {
    private final MockMvc mockMvc;

    @MockBean
    private AsutrEmployeeService service;

    @Autowired
    public EdeExceptionHandlerMockTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void handleRuntimeException_whenServiceThrowsException_thenReturnsCorrectExceptionResponse()
            throws Exception {
        when(service.getAsutrEmployeeDtosByPersonnelNumbers(Mockito.anyList()))
                .thenThrow(new IndexOutOfBoundsException(777));

        mockMvc.perform(get("/api/v1/employees?emplNumbers=123,4567,890")
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.exception").value("IndexOutOfBoundsException"))
                .andExpect(jsonPath("$.message").value("Index out of range: 777"))
                .andExpect(jsonPath("$.timeStamp").isNotEmpty())
                .andExpect(jsonPath("$.details").isNotEmpty());

        verify(service, Mockito.times(1))
                .getAsutrEmployeeDtosByPersonnelNumbers(Mockito.anyList());
    }

    @ParameterizedTest
    @ValueSource(strings = { "[123,4567,890]", "245к" })
    @NullSource
    public void handleRuntimeException_whenGetIncorrectParameterFormat_thenReturnsCorrectExceptionResponse(String value)
            throws Exception {
        when(service.getAsutrEmployeeDtosByPersonnelNumbers(Mockito.anyList()))
                .thenReturn(new AsutrEmployeeDto[] { EdeTestDataCreator.createAsutrEmployeeDto() });

        mockMvc.perform(get("/api/v1/employees?emplNumbers=" + value)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.exception").value("MethodArgumentTypeMismatchException"))
                .andExpect(jsonPath("$.message").value("Failed to convert value of type " +
                        "'java.lang.String' to required type 'java.util.List'; For input string: \"" + value + "\""))
                .andExpect(jsonPath("$.timeStamp").isNotEmpty())
                .andExpect(jsonPath("$.details").isNotEmpty());

        verify(service, Mockito.never()).getAsutrEmployeeDtosByPersonnelNumbers(Mockito.anyList());
    }


}