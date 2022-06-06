package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController extends  Controller<Film>{
    private long genId = 1;
    private final static LocalDate NO_BEFORE = LocalDate.of(1895, Month.DECEMBER, 28);

    private final long generateId() {
        return genId++;
    }

    @GetMapping
    public Set<Film> getT() {
        return storage;
    }

    @PostMapping
    public Film postT(@Valid @RequestBody Film film) throws ValidationException{
        boolean isValid = NO_BEFORE.isBefore(film.getReleaseDate())
                && film.getDuration() > 0;

        if (!isValid){
            log.warn("Возникла ошибка при сохранении фильма");
            throw new ValidationException();
        }
            film.setId(generateId());
            storage.add(film);

        return film;
    }

    @PutMapping
    public Film putT(@Valid @RequestBody Film film) throws ValidationException{
        boolean isValid = NO_BEFORE.isBefore(film.getReleaseDate())
                && film.getDuration() > 0
                && film.getId() > 0;


        if (!isValid){
            log.warn("Возникла ошибка при обновлении фильма");
            throw new ValidationException();
        }

        if (storage.contains(film)) {
            storage.remove(film);
            storage.add(film);
        }


        return film;
    }
}
