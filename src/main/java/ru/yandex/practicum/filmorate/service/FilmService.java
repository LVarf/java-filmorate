package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundObjectException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService extends Services<Film> {
    private final static LocalDate NO_BEFORE = LocalDate.of(1895, Month.DECEMBER, 28);
    private final InMemoryFilmStorage inMemoryFilmStorage;
    private final UserService userService;

    @Autowired
    public FilmService(InMemoryFilmStorage inMemoryFilmStorage, UserService userService) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
        this.userService = userService;
    }

    @Override
    public Set<Film> getAllT() {
        return inMemoryFilmStorage.getStorage();
    }

    @Override
    public Film getT(long idFilm) {
        if(!inMemoryFilmStorage.getStorage().contains(Film.builder().id(idFilm).build()))
            throw new NotFoundObjectException(String.format("Фильма с id %s нет в списке", idFilm));

        for (Film film : inMemoryFilmStorage.getStorage()) {
            if(film.getId() == idFilm) {
                return film;
            }
        }

        return null;
    }

    @Override
    public  Film postT(Film film){
        boolean isValid = NO_BEFORE.isBefore(film.getReleaseDate());

        if (!isValid){
            log.warn("Ошибка в дате релиза фильма");
            throw new ValidationException("Ошибка в дате релиза фильма");
        }
        film.setId(generateId());
        inMemoryFilmStorage.getStorage().add(film);
        film.setLikes(new HashSet<>());

        return film;
    }

    @Override
    public Film putT(Film film) {
        boolean isValid = NO_BEFORE.isBefore(film.getReleaseDate())
                && film.getDuration() > 0
                && inMemoryFilmStorage.getStorage().contains(film);


        if (!isValid){
            log.warn("Возникла ошибка при обновлении фильма");
            throw new NotFoundObjectException("Возникла ошибка при обновлении фильма");
        }

        for (Film films : inMemoryFilmStorage.getStorage()) {
            if (films.equals(film)) {
                film.setLikes(films.getLikes());
            }
        }

        if (inMemoryFilmStorage.getStorage().contains(film)) {
            inMemoryFilmStorage.getStorage().remove(film);
            inMemoryFilmStorage.getStorage().add(film);
        }
        return film;
    }

    @Override
    public boolean putIdToSet(long idFilm, long idUser) {
        if(!inMemoryFilmStorage.getStorage().contains(Film.builder().id(idFilm).build()))
            throw new NotFoundObjectException(String.format("Фильма с id %s нет в списке", idFilm));
        if(!userService.getAllT().contains(User.builder().id(idUser).build()))
            throw new NotFoundObjectException(String.format("Пользователя с id %s нет в списке", idUser));

        for(Film film: inMemoryFilmStorage.getStorage()) {
            if (film.getId() == idFilm) {
                if (!film.getLikes().contains(idUser)) {
                    film.getLikes().add(idUser);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
   public boolean deleteFromSet(long idFilm, long idUser) {
        if(!inMemoryFilmStorage.getStorage().contains(Film.builder().id(idFilm).build()))
            throw new NotFoundObjectException(String.format("Фильма с id %s нет в списке", idFilm));
        if(!userService.getAllT().contains(User.builder().id(idUser).build()))
            throw new NotFoundObjectException(String.format("Пользователя с id %s нет в списке", idUser));

        for(Film film: inMemoryFilmStorage.getStorage()) {
            if (film.getId() == idFilm) {
                if (film.getLikes().contains(idUser)) {
                    film.getLikes().remove(idUser);
                    return true;
                }
            }
        }
        return false;
    }

    public Set<Film> getPopularFilm(long count){
        Set<Film> popularFilms = inMemoryFilmStorage.getStorage()
                .stream()
                .sorted((o1, o2) -> (o2.getLikes().size() - o1.getLikes().size()))
                .limit(count)
                .collect(Collectors.toSet());

        return popularFilms;
    }
}
