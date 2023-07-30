package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.time.LocalDate;
import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {

    private static final LocalDate START_CINEMA = LocalDate.of(1895, 12, 28);
    private final FilmStorage filmStorage;

    public Collection<Film> findAllFilms() {
        return filmStorage.findAllFilms();
    }

    public Film findFilm(long id) {
        return filmStorage.findFilm(id);
    }

    public Collection<Film> findPopularFilms(int count) {
        return filmStorage.findPopularFilms(count);
    }

    public Film createFilm(Film film) {
        validateReleaseDate(film.getReleaseDate());
        return filmStorage.createFilm(film);
    }

    public Film updateFilm(Film film) {
        validateReleaseDate(film.getReleaseDate());
        return filmStorage.updateFilm(film);
    }

    public void deleteFilm(long id) {
        filmStorage.deleteFilm(id);
    }

    public void addLike(long id, long userId) {
        filmStorage.addLike(id, userId);
    }

    public void deleteLike(long id, long userId) {
        filmStorage.deleteLike(id, userId);
    }

    private void validateReleaseDate(LocalDate releaseDate) {
        if (releaseDate.isBefore(START_CINEMA)) {
            String message = "Release date should not be earlier than December 28, 1895.";
            log.info("Invalid release date: {}. {}", releaseDate, message);
            throw new ValidationException(message);
        }
    }
}