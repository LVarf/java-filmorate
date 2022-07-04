package ru.yandex.practicum.filmorate.storage;

import java.util.HashSet;
import java.util.Set;

public interface Storage <T>{

    public void addT(T t);

    public void update(T t);
}
