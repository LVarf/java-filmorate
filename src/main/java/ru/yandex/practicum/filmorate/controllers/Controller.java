package ru.yandex.practicum.filmorate.controllers;


import java.util.Set;

public abstract class Controller <T> {


    public abstract Set<T> getAllT();

    public abstract T postT(T t);

    public abstract T putT(T t);

    public abstract T getT(long id);

    public abstract boolean putIdToSet(long id1, long id2);

    public abstract boolean deleteFromSet(long id1, long id2);



}
