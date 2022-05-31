package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController extends Controller<User>{

    private long genId = 1;
    private final Set<User> users = new HashSet<>();

    @GetMapping
    public Set<User> getT() {
        return users;
    }

    private final long generateId() {
        return genId++;
    }


    @PostMapping
    public User postT(@Valid @RequestBody User user) throws ValidationException {
        boolean isValid = !user.getLogin().contains(" ")
                && user.getBirthday().isBefore(LocalDate.now());
        if (user.getName().isEmpty())
            user.setName(user.getLogin());


        if (!isValid){
            log.warn("Произошла ошибка валидации при создании пользователя");
            throw new ValidationException();
        }
        user.setId(generateId());
        users.add(user);

        return user;
    }

    @PutMapping
    public User putT(@Valid @RequestBody User user) throws ValidationException{
        boolean isValid = !user.getLogin().contains(" ")
                && user.getBirthday().isBefore(LocalDate.now())
                && user.getId() > 0;
        if (user.getName().isEmpty())
            user.setName(user.getLogin());


        if (!isValid) {
            log.warn("Произошла ошибка валидации при обновлении данных пользователя");
            throw new ValidationException();
        }

        if (users.contains(user)) {
            users.remove(user);
            users.add(user);
        }

        return user;
    }
}
