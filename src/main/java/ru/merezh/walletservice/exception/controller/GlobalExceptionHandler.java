package ru.merezh.walletservice.exception.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.merezh.walletservice.exception.WalletException;
import ru.merezh.walletservice.exception.dto.ExceptionDto;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(WalletException.class)
    public ResponseEntity<ExceptionDto> walletExceptionHandler(WalletException e) {
        return ResponseEntity.status(e.getCode()).body(new ExceptionDto(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> exceptionHandler(Exception e) {
        log.error("Ошибка в классе - {} - {}", e.getClass(), e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionDto("Ошибка при работе с сервисом"));
    }
}
