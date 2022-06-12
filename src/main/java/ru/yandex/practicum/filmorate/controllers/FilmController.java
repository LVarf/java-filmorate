package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController extends  Controller<Film>{
    private final static String  DEFAULT_VALUE_COUNT = "10";
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }


    @Override
    @GetMapping
    public Set<Film> getAllT() {
        return filmService.getAllT();
    }//moved

    @Override
    @GetMapping("/{id}")
    public Film getT(@PathVariable("id") long id) {
        return filmService.getT(id);
    }

    @Override
    @PostMapping
    public Film postT(@Valid @RequestBody Film film){//moved
        return filmService.postT(film);
    }

    @Override
    @PutMapping
    public Film putT(@Valid @RequestBody Film film){//moved
        return filmService.putT(film);
    }

    @Override
    @PutMapping("/{id}/like/{userId}")
    public boolean putIdToSet(@PathVariable("id") long id, @PathVariable("userId") long userId) {
        return filmService.putIdToSet(id, userId);
    }

    @Override
    @DeleteMapping("/{id}/like/{userId}")
    public boolean deleteFromSet(@PathVariable("id") long id, @PathVariable("userId") long userId){
        return filmService.deleteFromSet(id, userId);
    }

    @GetMapping("/popular")
    public Set<Film> getPopularFilms(
            @RequestParam(defaultValue = DEFAULT_VALUE_COUNT,
            required = false) String count
    ) {
        return filmService.getPopularFilm(Long.parseLong(count));
    }
}
