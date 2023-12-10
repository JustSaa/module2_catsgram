package ru.yandex.practicum.catsgram.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    public ResponseEntity<List<String>> InvalidEmailExceptionHandler(final InvalidEmailException e) {
        return new ResponseEntity<>(List.of(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<List<String>> UserAlreadyExistExceptionHandler(final UserAlreadyExistException e) {
        return new ResponseEntity<>(List.of(e.getMessage()), HttpStatus.NOT_ACCEPTABLE);
    }
}
