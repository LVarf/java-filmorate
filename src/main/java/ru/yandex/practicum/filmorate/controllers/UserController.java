package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
public class UserController extends Controller<User>{

    private final UserService userService;

    @Override
    @GetMapping
    public Set<User> getAllT() {
        return userService.getAllT();
    }

    @Override
    @GetMapping("/{id}")
    public User getT(@PathVariable("id") long id) {
        return userService.getT(id);
    }

    @Override
    @PutMapping
    public User putT(@Valid @RequestBody User user) {
        return userService.putT(user);
    }

    @Override
    @PostMapping
    public User postT(@Valid @RequestBody User user) {
        return userService.postT(user);
    }

    @Override
    @PutMapping("/{id}/friends/{friendId}")
    public boolean putIdToSet(@PathVariable("id") long idUser, @PathVariable("friendId") long idFriend) {
        return userService.putIdToSet(idUser, idFriend);
    }

    @Override
    @DeleteMapping("/{id}/friends/{friendId}")
    public boolean deleteFromSet(@PathVariable("id") long idUser, @PathVariable("friendId") long idFriend) {
        return userService.deleteFromSet(idUser, idFriend);
    }

    @GetMapping("/{id}/friends")
    public Set<User> getFriends (@PathVariable("id") long id) {
        return userService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Set<User> getMutualFriends(@PathVariable("id") long idUser, @PathVariable("otherId") long otherId) {
        return userService.getMutualFriends(idUser, otherId);
    }

}
