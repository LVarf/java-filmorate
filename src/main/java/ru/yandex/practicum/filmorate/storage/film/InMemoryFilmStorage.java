package ru.yandex.practicum.filmorate.storage.film;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundObjectException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.HashSet;
import java.util.Set;

@Component
@Getter
public class InMemoryFilmStorage implements Storage<Film> {

    private Set<Film> storage = new HashSet<>();

    @Override
    public void addT(Film film) {
        storage.add(film);
    }

    @Override
    public void update(Film film) {

        if(!storage.contains(film))
            throw new NotFoundObjectException("Фильм не найден");

        storage.remove(film);
        storage.add(film);
    }
}
