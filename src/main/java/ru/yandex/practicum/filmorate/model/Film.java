package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@EqualsAndHashCode
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class Film {
    private long id;
    @NotBlank
    @EqualsAndHashCode.Exclude
    private String name;
    @EqualsAndHashCode.Exclude
    @Size(min = 1, max = 200)
    private String description;
    @EqualsAndHashCode.Exclude
    private LocalDate releaseDate;
    @EqualsAndHashCode.Exclude
    @Min(1)
    private long duration;
}
