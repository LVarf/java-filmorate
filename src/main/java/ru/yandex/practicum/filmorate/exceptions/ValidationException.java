package ru.yandex.practicum.filmorate.exceptions;

public class ValidationException extends IllegalArgumentException {

    public ValidationException() {

    }

    @Override
    public String getMessage() {
        return "Возникла ошибка валидации";
    }
}
