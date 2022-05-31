package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

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
}
