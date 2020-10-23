package org.innopolis.kuzymvas.secondexam.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Класс для обработчиков исключений на уровне приложения
 */
@ControllerAdvice
public class ExceptionHandlingAdvise {

    private final Logger logger = LoggerFactory.getLogger(ExceptionHandlingAdvise.class);

    /**
     * Обрабатывает исключения, вызванные читаемым, но некорректным запросом со стороны клиента
     *
     * @param exception - обрабатываемое исключение
     * @return - JSON ответ для отправки клиенту вместе с кодом 400
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public String handleClientError(IllegalArgumentException exception) {
        logger.debug("User request caused exception: "  + exception.getLocalizedMessage());
        return exception.getLocalizedMessage();
    }

    /**
     * Обрабатывает исключения, вызванные нечитаемым запросом со стороны клиента
     *
     * @param exception - обрабатываемое исключение
     * @return - JSON ответ для отправки клиенту вместе с кодом 400
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public String handleRequestError(HttpMessageNotReadableException exception) {
        logger.debug("User send in unreadable request: "
                             + exception.getLocalizedMessage());
        return "Request body is invalid";
    }


    /**
     * Обрабатывает исключения, вызванные ошибкой в работе БД или запросах к ней
     *
     * @param exception - обрабатываемое исключение
     * @return - JSON ответ для отправки клиенту вместе с кодом 500
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  // 500
    @ExceptionHandler(DataAccessException.class)
    @ResponseBody
    public String handleDatabaseError(DataAccessException exception) {
        logger.error("DB caused exception: "
                             + exception.getLocalizedMessage());
        return "Database failure during request processing";
    }

    /**
     * Обрабатывает исключения, вызванные ошибкой логики работы приложения
     *
     * @param exception - обрабатываемое исключение
     * @return - JSON ответ для отправки клиенту вместе с кодом 500
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  // 500
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String handleServerError(Exception exception) {
        logger.error("Internal logic caused exception: "
                             + exception.getLocalizedMessage());
        return "Server failure during request processing";
    }
}
