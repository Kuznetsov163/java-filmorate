package ru.yandex.practicum.filmorate.storage.film;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.LocalDate;
import java.util.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;



   @Slf4j
   public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> films = new HashMap<>();

    private long id = 1;


    @PostMapping
    public Film createFilm(@RequestBody @Valid Film film) {
        validateFilm(film);
        film.setId(id++);
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody @Valid Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("Дата релиза не может быть раньше 28 декабря 1895 года");
            throw new ValidationException("Дата релиза не может быть раньше 28 декабря 1895 года");
        } else if (film.getId() == null) {
            throw new ValidationException("ИД изменямого фильма не может быть пустым");
        } else if (!films.containsKey(film.getId())) {
            throw new NotFoundException("Не найден изменяемый фильм");
        }
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Optional<Film> getFilmId(Long filmId) {
        return Optional.ofNullable(films.get(filmId));
    }

    @GetMapping
    public Collection<Film> getAllFilm() {
        if (films.isEmpty()) {
            log.info("Список фильмов пуст");
            throw new NotFoundException("Список фильмов пуст");
        }
        return films.values();
    }

    @Override
    public void addLike(Long filmId, Long userId) {
    }

    @Override
    public void removeLike(Long filmId, Long userId) {
    }

    @Override
    public List<Film> getTopFilms(long count) {
        return null;
    }

    private void validateFilm(Film film) {
        if (film.getName().isBlank()) {
            log.warn("Название фильма не может быть пустым");
            throw new ValidationException("Название фильма не может быть пустым");
        }
        if (film.getDescription().length() > 200) {
            log.warn("Описание фильма не может превышать 200 символов");
            throw new ValidationException("Описание фильма не может превышать 200 символов");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("Дата релиза не может быть раньше 28 декабря 1895 года");
            throw new ValidationException("Дата релиза не может быть раньше 28 декабря 1895 года");
        }
        if (film.getDuration() <= 0) {
            log.warn("Продолжительность фильма должна быть положительным числом");
            throw new ValidationException("Продолжительность фильма должна быть положительным числом");
        }
    }

       @ExceptionHandler
       @ResponseStatus(HttpStatus.BAD_REQUEST)
       public Map<String, String> handleValidation(final ValidationException e) {
           return Map.of("error", "Произошла ошибка валидации одного из параметров: " + e.getMessage());
       }

       @ExceptionHandler
       @ResponseStatus(HttpStatus.NOT_FOUND)
       public Map<String, String> handleNotFound(final NotFoundException e) {
           return Map.of("error", "Не найден переданный параметр.");
       }
}

