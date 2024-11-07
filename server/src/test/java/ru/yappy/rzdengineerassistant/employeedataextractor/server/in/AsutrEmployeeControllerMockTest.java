package ru.yappy.rzdengineerassistant.employeedataextractor.server.in;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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

@WebMvcTest(AsutrEmployeeController.class)
public class AsutrEmployeeControllerMockTest {
    private final MockMvc mockMvc;

    @MockBean
    private AsutrEmployeeService service;

    @Autowired
    public AsutrEmployeeControllerMockTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @ParameterizedTest
    @ValueSource(strings = { "123,4567", "", "  ", "\t" })
    public void getEmployeeDataByAsutrPersonnelNumbers_whenHaveNoProblem_thenReturnCorrectDto(String value)
            throws Exception {
        when(service.getAsutrEmployeeDtosByPersonnelNumbers(Mockito.anyList()))
                .thenReturn(new AsutrEmployeeDto[] { EdeTestDataCreator.createAsutrEmployeeDto() });

        mockMvc.perform(get("/api/v1/employees?emplNumbers=" + value)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].asutrPersonnelNumber").value(1234567))
                .andExpect(jsonPath("$[0].lastName").value("Тестов"))
                .andExpect(jsonPath("$[0].firstName").value("Тест"))
                .andExpect(jsonPath("$[0].patronymic").value("Тестович"))
                .andExpect(jsonPath("$[0].employeeType").value("РАБОЧИЙ"))
                .andExpect(jsonPath("$[0].jobTitleAbbreviation").value("Т"))
                .andExpect(jsonPath("$[0].fullJobTitle").value("Тестировщик путей сообщения"))
                .andExpect(jsonPath("$[0].employmentDate").value("01-01-2022"))
                .andExpect(jsonPath("$[0].employmentLength").value(2.8))
                .andExpect(jsonPath("$[0].atPositionSince").value("01-01-2023"))
                .andExpect(jsonPath("$[0].atPositionJobLength").value(1.8))
                .andExpect(jsonPath("$[0].edType").value("HIGHER_EDUCATION"))
                .andExpect(jsonPath("$[0].edInstitutionName")
                        .value("Кусайкинская высшая тестировочная академия"))
                .andExpect(jsonPath("$[0].faculty").value("Факультет прикладного тестирования"))
                .andExpect(jsonPath("$[0].edGraduationYear").value(2022));

        verify(service, Mockito.times(1))
                .getAsutrEmployeeDtosByPersonnelNumbers(Mockito.anyList());
    }

}