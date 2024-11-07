package ru.yappy.rzdengineerassistant.employeedataextractor.server.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Formula;
import ru.yappy.rzdengineerassistant.employeedataextractor.dtos.EducationType;

import java.time.LocalDate;

/**
 * Entity-класс, содержащий все поля, характерные для сущности сотрудника в базе данных приложения АСУТР.
 * <p> Названия полей идентичны {@link ru.yappy.rzdengineerassistant.employeedataextractor.dtos.AsutrEmployeeDto} для
 * удобства мэппинга объектов.
 */
@Getter
@Setter
@Entity
@Table(name = "asutr_employees")
public class AsutrEmployeeEntity {
    /** Табельный номер сотрудника - идентификатор в базе данных АСУТР. */
    @Id
    @OrderBy
    private Long asutrPersonnelNumber;
    /** Фамилия сотрудника. */
    private String lastName;
    /** Имя сотрудника. */
    private String firstName;
    /** Отчество сотрудника. */
    private String patronymic;
    /** Тип сотрудника. */
    @Column(name = "type")
    private String employeeType;
    /** Аббревиатура должности. */
    private String jobTitleAbbreviation;
    /** Полное название должности. */
    private String fullJobTitle;
    /** Дата трудоустройства в компанию ОАО "РЖД". */
    private LocalDate employmentDate;
    /** Продолжительность работы в компании. */
    @Formula("""
            (CAST((CURRENT_DATE - employment_date) AS REAL) / 365)
            """)
    private Float employmentLength;
    /** Дата начала работы в текущей должности. */
    private LocalDate atPositionSince;
    /** Продолжительность работы в текущей должности. */
    @Formula("""
            (CAST((CURRENT_DATE - at_position_since) AS REAL) / 365)
            """)
    private Float atPositionJobLength;
    /** Тип образования. */
    @Column(name = "education_type")
    @Enumerated(EnumType.STRING)
    private EducationType edType;
    /** Название учебного заведения. */
    private String edInstitutionName;
    /** Факультет, специальность. */
    private String faculty;
    /** Год окончания учебного заведения. */
    private Integer edGraduationYear;

}