package ru.yandex.practicum.filmorate.exceptions;

public class ValidationException extends IllegalArgumentException {

    public ValidationException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Возникла ошибка валидации";
    }
}
