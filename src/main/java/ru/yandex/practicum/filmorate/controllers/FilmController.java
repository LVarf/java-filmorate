package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private long genId = 1;
    private final Set<Film> films = new HashSet<>();
    private final static LocalDate NO_BEFORE = LocalDate.of(1895, Month.DECEMBER, 28);

    private final long generateId() {
        return genId++;
    }

    @GetMapping
    public Set<Film> getFilms() {
        return films;
    }

    @PostMapping
    public Film postFilm(@RequestBody Film film) throws ValidationException{
        boolean isValid = !film.getName().isBlank()
                && (film.getDescription().length() <= 200)
                && NO_BEFORE.isBefore(film.getReleaseDate())
                && film.getDuration() > 0;

        if (!isValid){
            log.warn("Возникла ошибка при сохранении фильма");
            throw new ValidationException();
        }
            film.setId(generateId());
            films.add(film);

        return film;
    }

    @PutMapping
    public Film putFilm(@RequestBody Film film) throws ValidationException{
        boolean isValid = !film.getName().isBlank()
                && (film.getDescription().length() <= 200)
                && NO_BEFORE.isBefore(film.getReleaseDate())
                && film.getDuration() > 0
                && film.getId() > 0;


        if (!isValid){
            log.warn("Возникла ошибка при обновлении фильма");
            throw new ValidationException();
        }

        if (films.contains(film)) {
            films.remove(film);
            films.add(film);
        }


        return film;
    }
}
