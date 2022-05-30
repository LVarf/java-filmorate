package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private long genId = 1;
    private final Map<String, User> users = new HashMap<>();

    @GetMapping
    public Map<String, User> getUsers() {
        return users;
    }

    private final long generateId() {
        return genId++;
    }


    @PostMapping
    public User postUser(@RequestBody User user) throws ValidationException {
        boolean isValid = !user.getEmail().isBlank()
                && user.getEmail().contains("@")
                && !user.getLogin().isBlank()
                && !user.getLogin().contains(" ")
                && user.getBirthday().isBefore(LocalDate.now());
        if (user.getName().isEmpty())
            user.setName(user.getLogin());


        if (!isValid){
            log.warn("Произошла ошибка валидации при создании пользователя");
            throw new ValidationException();
        }
        user.setId(generateId());
        users.put(user.getEmail(), user);

        return user;
    }

    @PutMapping
    public User putUser(@RequestBody User user) throws ValidationException{
        boolean isValid = !user.getEmail().isBlank()
                && user.getEmail().contains("@")
                && !user.getLogin().isBlank()
                && !user.getLogin().contains(" ")
                && user.getBirthday().isBefore(LocalDate.now());
        if (user.getName().isEmpty())
            user.setName(user.getLogin());


        if (!isValid) {
            log.warn("Произошла ошибка валидации при обновлении данных пользователя");
            throw new ValidationException();
        }

        if (users.containsKey(user.getEmail())) {
            users.remove(user.getEmail());
            users.put(user.getEmail(), user);
        } else
            users.put(user.getEmail(), user);

        return user;
    }
}
