package ru.yappy.rzdengineerassistant.employeedataextractor.server.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yappy.rzdengineerassistant.employeedataextractor.dtos.AsutrEmployeeDto;
import ru.yappy.rzdengineerassistant.employeedataextractor.dtos.EdeTestDataCreator;
import ru.yappy.rzdengineerassistant.employeedataextractor.dtos.EducationType;
import ru.yappy.rzdengineerassistant.employeedataextractor.server.model.AsutrEmployeeEntity;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AsutrEmployeeMapperIntTest {
    private final AsutrEmployeeMapper mapper;

    @Autowired
    public AsutrEmployeeMapperIntTest(AsutrEmployeeMapper mapper) {
        this.mapper = mapper;
    }

    @Test
    public void toDtoArray_whenGetCorrectEntityArray_thenReturnDtoArray() {
        assertThat(mapper.toDtoArray(new AsutrEmployeeEntity[] { createAsutrEmployeeEntity() }))
                .isEqualTo(new AsutrEmployeeDto[] { EdeTestDataCreator.createAsutrEmployeeDto() });
    }

    @Test
    public void toDtoArray_whenGetNullArray_thenReturnNull() {
        assertThat(mapper.toDtoArray(null)).isNull();
    }

    @Test
    public void toDtoArray_whenGetArrayWithNullEntity_thenReturnArrayWithNull() {
        assertThat(mapper.toDtoArray(new AsutrEmployeeEntity[] { null })).isEqualTo(new AsutrEmployeeDto[] { null });
    }

    @Test
    public void toDtoArray_whenGetEmptyArray_thenReturnEmptyArray() {
        assertThat(mapper.toDtoArray(new AsutrEmployeeEntity[0])).isEmpty();
    }

    private AsutrEmployeeEntity createAsutrEmployeeEntity() {
        AsutrEmployeeEntity entity = new AsutrEmployeeEntity();
        entity.setAsutrPersonnelNumber(1234567L);
        entity.setLastName("Тестов");
        entity.setFirstName("Тест");
        entity.setPatronymic("Тестович");
        entity.setEmployeeType("РАБОЧИЙ");
        entity.setJobTitleAbbreviation("Т");
        entity.setFullJobTitle("Тестировщик путей сообщения");
        entity.setEmploymentDate(LocalDate.of(2022, 1, 1));
        entity.setEmploymentLength(2.8f);
        entity.setAtPositionSince(LocalDate.of(2023, 1, 1));
        entity.setAtPositionJobLength(1.8f);
        entity.setEdType(EducationType.HIGHER_EDUCATION);
        entity.setEdInstitutionName("Кусайкинская высшая тестировочная академия");
        entity.setFaculty("Факультет прикладного тестирования");
        entity.setEdGraduationYear(2022);
        return entity;
    }

}