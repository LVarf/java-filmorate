package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
@NonNull
@Builder(toBuilder = true)
public class User {
    private long id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;
}
