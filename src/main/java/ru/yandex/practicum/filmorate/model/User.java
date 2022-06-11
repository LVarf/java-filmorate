package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class User {
    private long id;
    @NotBlank
    @Email
    @EqualsAndHashCode.Exclude
    private String email;
    @NotBlank
    @EqualsAndHashCode.Exclude
    private String login;
    @EqualsAndHashCode.Exclude
    private String name;
    @EqualsAndHashCode.Exclude
    private LocalDate birthday;
    @EqualsAndHashCode.Exclude
    private Set<Long> friends;
}
