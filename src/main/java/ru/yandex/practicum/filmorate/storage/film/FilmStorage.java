package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

public interface FilmStorage {

    Film findFilm(long id);

    Collection<Film> findAllFilms();

    Film createFilm(Film film);

    Film updateFilm(Film film);

    void deleteFilm(long id);

    void addLike(long id, long userId);

    void deleteLike(long id, long userId);

    Collection<Film> findPopularFilms(int count);

}
