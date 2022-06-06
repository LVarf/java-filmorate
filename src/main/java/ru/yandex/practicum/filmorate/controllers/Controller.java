package ru.yandex.practicum.filmorate.controllers;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class Controller <T> {

    protected Set<T> storage = new HashSet<>();

    @GetMapping
    abstract Set<T> getT();

    @PostMapping
    abstract T postT(@Valid @RequestBody T t);

    @PutMapping
    abstract T putT(@Valid @RequestBody T t);

}
