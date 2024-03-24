package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.NotNull;

@Builder
@Getter
@Setter
@EqualsAndHashCode
public class Genre {

    @NotNull
    private final int id;
    private final String name;

}
