package ru.yandex.practicum.filmorate.storage.mpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class InMemoryMpaStorage implements MpaStorage {

    Map<Integer, Mpa> mpaData = Map.of(
            1, Mpa.builder().id(1).name("G").build(),
            2, Mpa.builder().id(2).name("PG").build(),
            3, Mpa.builder().id(3).name("PG-13").build(),
            4, Mpa.builder().id(4).name("R").build(),
            5, Mpa.builder().id(5).name("NC-17").build());

    @Override
    public Mpa findMpa(int id) {
        if (mpaData.containsKey(id)) {
            return mpaData.get(id);
        } else {
            log.info("Рейтинг с id = {} не найден.", id);
            throw new NotFoundException("Рейтинг с id = " + id + " не найден.");
        }
    }

    @Override
    public Collection<Mpa> findAllMpa() {
        return mpaData.values().stream().sorted(Comparator.comparingInt(Mpa::getId)).collect(Collectors.toList());
    }

}
