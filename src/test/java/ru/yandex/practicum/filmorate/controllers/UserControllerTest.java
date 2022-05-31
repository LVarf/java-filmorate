package ru.yandex.practicum.filmorate.controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    static User user;

    @BeforeAll
    static void makeUser() {
        user = User.builder()
                .id(1)
                .email("email@mail.ru")
                .login("login")
                .name("namename")
                .birthday(LocalDate.of(2000, Month.NOVEMBER, 29))
                .build();
    }

    @Test
    void postUser() {

        Controller userController= new UserController();

        ValidationException ex = assertThrows(
                ValidationException.class,
                () -> userController.postT(user.toBuilder()
                        .email("   ")
                        .build())
        );
        assertEquals("Возникла ошибка валидации", ex.getMessage());

        ex = assertThrows(
                ValidationException.class,
                () -> userController.postT(user.toBuilder()
                        .email("email-mail.ru")
                        .build())
        );
        assertEquals("Возникла ошибка валидации", ex.getMessage());

        ex = assertThrows(
                ValidationException.class,
                () -> userController.postT(user.toBuilder()
                        .login("  ")
                        .build())
        );
        assertEquals("Возникла ошибка валидации", ex.getMessage());

        ex = assertThrows(
                ValidationException.class,
                () -> userController.postT(user.toBuilder()
                        .login("log in")
                        .build())
        );
        assertEquals("Возникла ошибка валидации", ex.getMessage());

        ex = assertThrows(
                ValidationException.class,
                () -> userController.postT(user.toBuilder()
                        .birthday(LocalDate.now().plusDays(1))
                        .build())
        );
        assertEquals("Возникла ошибка валидации", ex.getMessage());
    }

    @Test
    void putUser() {
        Controller userController= new UserController();

        ValidationException ex = assertThrows(
                ValidationException.class,
                () -> userController.putT(user.toBuilder()
                        .email("   ")
                        .build())
        );
        assertEquals("Возникла ошибка валидации", ex.getMessage());

        ex = assertThrows(
                ValidationException.class,
                () -> userController.putT(user.toBuilder()
                        .email("email-mail.ru")
                        .build())
        );
        assertEquals("Возникла ошибка валидации", ex.getMessage());

        ex = assertThrows(
                ValidationException.class,
                () -> userController.putT(user.toBuilder()
                        .login("   ")
                        .build())
        );
        assertEquals("Возникла ошибка валидации", ex.getMessage());

        ex = assertThrows(
                ValidationException.class,
                () -> userController.putT(user.toBuilder()
                        .login("log in")
                        .build())
        );
        assertEquals("Возникла ошибка валидации", ex.getMessage());

        ex = assertThrows(
                ValidationException.class,
                () -> userController.putT(user.toBuilder()
                        .birthday(LocalDate.now().plusDays(1))
                        .build())
        );
        assertEquals("Возникла ошибка валидации", ex.getMessage());
    }
}