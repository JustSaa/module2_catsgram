package ru.yandex.practicum.catsgram.exeption;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(String message) {
        super(message);
    }
}
