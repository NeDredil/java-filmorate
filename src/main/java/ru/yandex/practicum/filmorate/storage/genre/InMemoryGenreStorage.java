package ru.yandex.practicum.filmorate.storage.genre;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class InMemoryGenreStorage implements GenreStorage {

    Map<Integer, Genre> genreData = Map.of(
            1, Genre.builder().id(1).name("Комедия").build(),
            2, Genre.builder().id(2).name("Драма").build(),
            3, Genre.builder().id(3).name("Мультфильм").build(),
            4, Genre.builder().id(4).name("Триллер").build(),
            5, Genre.builder().id(5).name("Документальный").build(),
            6, Genre.builder().id(6).name("Боевик").build());

    @Override
    public Genre findGenre(int id) {
        if (genreData.containsKey(id)) {
            return genreData.get(id);
        } else {
            log.info("Жанр с id = {} не найден.", id);
            throw new NotFoundException("Жанр с id = " + id + " не найден.");
        }
    }

    @Override
    public Collection<Genre> findAllGenres() {
        return genreData.values().stream().sorted(Comparator.comparingInt(Genre::getId)).collect(Collectors.toList());
    }

}
