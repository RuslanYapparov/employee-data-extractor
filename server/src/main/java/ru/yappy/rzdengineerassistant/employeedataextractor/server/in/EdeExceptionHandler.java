package ru.yappy.rzdengineerassistant.employeedataextractor.server.in;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yappy.rzdengineerassistant.commonclasses.component.ReaExceptionHandler;
import ru.yappy.rzdengineerassistant.commonclasses.dto.ExceptionDto;

/**
 * Класс SpringBoot-обработчика исключений, выброшенных в ходе работы сервиса EmployeeDataExtractor.
 */
@RestControllerAdvice
public class EdeExceptionHandler extends ReaExceptionHandler {

    /**
     * Обрабатывает все Runtime-исключения, выброшенные в ходе работы сервиса, для которых не реализована отдельная
     * обработка в данном классе.
     *
     * @param exception Runtime-исключение выброшенное в ходе работы приложения, для которого не реализована обработка
     * @return DTO c информацией об исключении
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionDto handleRuntimeException(RuntimeException exception) {
        return handleException(exception);
    }

}
