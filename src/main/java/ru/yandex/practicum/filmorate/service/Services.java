package ru.yandex.practicum.filmorate.service;

import java.util.Set;

public abstract class Services<T> {
    private long genId = 1;

    public long generateId() {
        return genId++;
    }

    public abstract Set<T> getAllT();

    public abstract T getT(long id);

    public abstract boolean putIdToSet(long idObject, long idUser);//Add a film's like or add as friend

    public abstract boolean deleteFromSet(long idObject, long idUser);//Delete a film's like or remove a friend

    public abstract T postT(T t);

    public abstract T putT(T t);
}
