package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Integer, Film> films = new HashMap<>();
    private int nextId = 1;
    private final Map<Integer, Set<Integer>> likes = new HashMap<>();

    @Override
    public Film create(Film film) {
        log.info("Создание нового фильма: {}", film);
        film.setId(nextId++);
        films.put(film.getId(), film);
        likes.put(film.getId(), new HashSet<>());
        return film;
    }

    @Override
    public Film update(Film film) {
        log.info("Обновление фильма: {}", film);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Optional<Film> get(int id) {
        log.info("Получение фильма по ID: {}", id);
        return Optional.ofNullable(films.get(id));
    }

    @Override
    public List<Film> getAll() {
        log.info("Получение всех фильмов");
        return new ArrayList<>(films.values());
    }

    @Override
    public void addLike(int filmId, int userId) {
        log.info("Добавление лайка к фильму {} от пользователя {}", filmId, userId);
        if (!likes.containsKey(filmId)) {
            likes.put(filmId, new HashSet<>());
        }
        likes.get(filmId).add(userId);
    }

    @Override
    public void removeLike(int filmId, int userId) {
        log.info("Удаление лайка к фильму {} от пользователя {}", filmId, userId);
        if (likes.containsKey(filmId)) {
            likes.get(filmId).remove(userId);
        }
    }

    @Override
    public List<Film> getTopFilms(int count) {
        log.info("Получение {} самых популярных фильмов", count);
        return likes.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().size() - e1.getValue().size())
                .limit(count)
                .map(e -> films.get(e.getKey()))
                .collect(Collectors.toList());
    }
}
