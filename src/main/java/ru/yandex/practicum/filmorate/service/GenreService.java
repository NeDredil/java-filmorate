package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreStorage genreStorage;

    public Genre findGenre(int id) {
        return genreStorage.findGenre(id);
    }

    public Collection<Genre> findAllGenres() {
        return genreStorage.findAllGenres();
    }
}