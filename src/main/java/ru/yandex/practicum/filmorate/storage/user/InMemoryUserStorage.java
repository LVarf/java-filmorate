package ru.yandex.practicum.filmorate.storage.user;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundObjectException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.HashSet;
import java.util.Set;

@Component
@Getter
public class InMemoryUserStorage implements Storage<User> {

    private Set<User> storage = new HashSet<>();

    @Override
    public void addT(User user) {
        storage.add(user);
    }

    @Override
    public void update(User user) {

        if(!storage.contains(user))
            throw new NotFoundObjectException("Пользователь не найден");

        storage.remove(user);
        storage.add(user);
    }
}
