package ru.yandex.practicum.filmorate.storage.mpa;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;

public interface MpaStorage {

    Mpa findMpa(int id);

    Collection<Mpa> findAllMpa();

}
