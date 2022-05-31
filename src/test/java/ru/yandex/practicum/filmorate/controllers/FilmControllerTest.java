package ru.yandex.practicum.filmorate.controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FilmControllerTest {

    static Film film;

    @BeforeAll
    static void makeUser() {
        film = Film.builder()
                .id(1)
                .name("filmName")
                .description("Some words about filmName")
                .releaseDate(LocalDate.of(2000, Month.NOVEMBER, 29))
                .duration(150)
                .build();
    }



    @Test
    void postFilm() {
        Controller filmController = new FilmController();

        ValidationException ex = assertThrows(
                ValidationException.class,
                () -> filmController.postT(film.toBuilder()
                        .name("   ")
                        .build())
        );
        assertEquals("Возникла ошибка валидации", ex.getMessage());

        ex = assertThrows(
                ValidationException.class,
                () -> filmController.postT(film.toBuilder()
                        .description("Для прохождения теста на длину описания меньше чем двести символов " +
                                "здесь должно быть больше, чем двести символов. Мне лень считать, поэтому..." +
                                " Я буду копипастить." + " Я буду копипастить." + " Я буду копипастить." +
                                " Я буду копипастить." + " Я буду копипастить." + " Я буду копипастить." +
                                " Я буду копипастить." + " Я буду копипастить." + " Я буду копипастить." + " Супер!")
                        .build())
        );
        assertEquals("Возникла ошибка валидации", ex.getMessage());

        ex = assertThrows(
                ValidationException.class,
                () -> filmController.postT(film.toBuilder()
                                .releaseDate(LocalDate.of(1895, Month.DECEMBER, 28).minusDays(1))
                        .build())
        );
        assertEquals("Возникла ошибка валидации", ex.getMessage());

        ex = assertThrows(
                ValidationException.class,
                () -> filmController.postT(film.toBuilder()
                        .duration(0)
                        .build())
        );
        assertEquals("Возникла ошибка валидации", ex.getMessage());
    }

    @Test
    void putFilm() {
        Controller filmController = new FilmController();

        ValidationException ex = assertThrows(
                ValidationException.class,
                () -> filmController.putT(film.toBuilder()
                        .name("   ")
                        .build())
        );
        assertEquals("Возникла ошибка валидации", ex.getMessage());

        ex = assertThrows(
                ValidationException.class,
                () -> filmController.putT(film.toBuilder()
                        .description("Для прохождения теста на длину описания меньше чем двести символов " +
                                "здесь должно быть больше, чем двести символов. Мне лень считать, поэтому..." +
                                " Я буду копипастить." + " Я буду копипастить." + " Я буду копипастить." +
                                " Я буду копипастить." + " Я буду копипастить." + " Я буду копипастить." +
                                " Я буду копипастить." + " Я буду копипастить." + " Я буду копипастить." + " Супер!")
                        .build())
        );
        assertEquals("Возникла ошибка валидации", ex.getMessage());

        ex = assertThrows(
                ValidationException.class,
                () -> filmController.putT(film.toBuilder()
                        .releaseDate(LocalDate.of(1895, Month.DECEMBER, 28).minusDays(1))
                        .build())
        );
        assertEquals("Возникла ошибка валидации", ex.getMessage());

        ex = assertThrows(
                ValidationException.class,
                () -> filmController.putT(film.toBuilder()
                        .duration(0)
                        .build())
        );
        assertEquals("Возникла ошибка валидации", ex.getMessage());
    }
}