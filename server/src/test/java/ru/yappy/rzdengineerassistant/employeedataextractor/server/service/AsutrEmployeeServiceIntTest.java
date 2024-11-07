package ru.yappy.rzdengineerassistant.employeedataextractor.server.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yappy.rzdengineerassistant.employeedataextractor.dtos.AsutrEmployeeDto;
import ru.yappy.rzdengineerassistant.employeedataextractor.dtos.EducationType;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AsutrEmployeeServiceIntTest {
    private final AsutrEmployeeService service;

    @Autowired
    public AsutrEmployeeServiceIntTest(AsutrEmployeeService service) {
        this.service = service;
    }

    @Test
    public void getAsutrEmployeeDtosByPersonnelNumbers_whenGetSuperListWithAllImitatingValues_thenReturnCorrectArray() {
        List<Long> superList = Stream.iterate(999_900L, n -> n < 1_000_200L, n -> n + 1)
                .collect(Collectors.toList());
        AsutrEmployeeDto[] asutrEmployeeDtos =
                service.getAsutrEmployeeDtosByPersonnelNumbers(superList);

        assertThat(asutrEmployeeDtos).isNotNull();
        assertThat(asutrEmployeeDtos.length).isEqualTo(109);
        assertThat(asutrEmployeeDtos[0].asutrPersonnelNumber()).isEqualTo(1000000L);
        assertThat(asutrEmployeeDtos[0].lastName()).isEqualTo("Шевцов");
        assertThat(asutrEmployeeDtos[0].edType()).isEqualTo(EducationType.HIGHER_PROFILE_EDUCATION);
        assertThat(asutrEmployeeDtos[0].edInstitutionName())
                .isEqualTo("Московский Государственный Университет Путей Сообщения");
        assertThat(asutrEmployeeDtos[0].atPositionJobLength()).isGreaterThan(3.0f);
        assertThat(asutrEmployeeDtos[0].employmentDate()).isEqualTo("1999-10-15");
        assertThat(asutrEmployeeDtos[57].asutrPersonnelNumber()).isEqualTo(1000057L);
        assertThat(asutrEmployeeDtos[57].lastName()).isEqualTo("Вишневская");
        assertThat(asutrEmployeeDtos[57].edType()).isEqualTo(EducationType.HIGHER_PROFILE_EDUCATION);
        assertThat(asutrEmployeeDtos[57].edInstitutionName())
                .isEqualTo("Свердловский Государственный Университет Путей Сообщения");
        assertThat(asutrEmployeeDtos[57].atPositionJobLength()).isGreaterThan(11.2f);
        assertThat(asutrEmployeeDtos[57].employmentDate()).isEqualTo("2007-04-09");
        assertThat(asutrEmployeeDtos[108].asutrPersonnelNumber()).isEqualTo(1000108L);
        assertThat(asutrEmployeeDtos[108].lastName()).isEqualTo("Пловин");
        assertThat(asutrEmployeeDtos[108].edType()).isEqualTo(EducationType.HIGHER_EDUCATION_NOT_ENDED);
        assertThat(asutrEmployeeDtos[108].edInstitutionName())
                .isEqualTo("Семеновский Институт Лесной Промышленности");
        assertThat(asutrEmployeeDtos[108].atPositionJobLength()).isGreaterThan(3.2f);
        assertThat(asutrEmployeeDtos[108].employmentDate()).isEqualTo("2009-08-04");
    }

    @Test
    public void getAsutrEmployeeDtosByPersonnelNumbers_whenGetWrongList_thenReturnEmptyArray() {
        List<Long> wrongList = List.of(Long.MAX_VALUE, Long.MIN_VALUE, 0L, -1L, 1L, 100_000L, 999_999L, 1_000_109L);
        AsutrEmployeeDto[] asutrEmployeeDtos =
                service.getAsutrEmployeeDtosByPersonnelNumbers(wrongList);

        assertThat(asutrEmployeeDtos).isNotNull();
        assertThat(asutrEmployeeDtos.length).isEqualTo(0);

        wrongList = List.of();
        asutrEmployeeDtos = service.getAsutrEmployeeDtosByPersonnelNumbers(wrongList);

        assertThat(asutrEmployeeDtos).isNotNull();
        assertThat(asutrEmployeeDtos.length).isEqualTo(0);

        wrongList = null;
        asutrEmployeeDtos = service.getAsutrEmployeeDtosByPersonnelNumbers(wrongList);

        assertThat(asutrEmployeeDtos).isNotNull();
        assertThat(asutrEmployeeDtos.length).isEqualTo(0);
    }

}