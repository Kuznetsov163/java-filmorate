package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Integer, Film> films = new HashMap<>();
    private int id = 1;


    @Override
    public Film create(Film film) {
        film.setId(id++);
        films.put(film.getId(), film);
        log.info("Фильм {} успешно добавлен", film.getName());
        return films.get(film.getId());

    }

    @Override
    public Film update(Film film) {
        if (!films.containsKey(film.getId())) {
            log.warn("Фильм с id {} не найден", film.getId());
            throw new NotFoundException("Фильм с таким id не найден");
        }
        films.put(film.getId(), film);
        log.info("Фильм '{}' успешно обновлен", film.getName());
        return film;
    }



    @Override
    public Film get(int id) {
        if (!films.containsKey(id)) {
            log.warn("Фильм с id {} не найден", id);
            throw new NotFoundException("Фильм с таким id не найден");
        }
        return films.get(id);
    }


    @Override
    public Set<Film> getAll() {
        log.info("Получение списка всех фильмов");
        return new HashSet<>(films.values());
    }


    public void addLike(int filmId, int userId) {
        if (!films.containsKey(filmId)) {
            log.warn("Фильм с id {} не найден", filmId);
            throw new NotFoundException("Фильм с таким id не найден");
        }
        films.get(filmId).getLikes().add(userId);
        log.info("Пользователь {} поставил лайк фильму {}", userId, filmId);
    }



    @Override
    public void removeLike(int filmId, int userId) {
        if (!films.containsKey(filmId)) {
            log.warn("Фильм с id {} не найден", filmId);
            throw new NotFoundException("Фильм с таким id не найден");
        }
        films.get(filmId).getLikes().remove(userId);
        log.info("Пользователь {} убрал лайк с фильма {}", userId, filmId);
    }



    @Override
    public Set<Film> getTopFilms(int count) {
        log.info("Получение {} самых популярных фильмов", count);
        return films.values().stream()
                .sorted((f1, f2) -> f2.getLikes().size() - f1.getLikes().size())
                .limit(count)
                .collect(Collectors.toSet());
    }

}

