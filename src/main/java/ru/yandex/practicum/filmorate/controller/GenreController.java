package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/genres")
@Qualifier("GenreDbStorage")
public class GenreController {
    private final GenreService genreService;

    public GenreController(@Autowired GenreService genreService) {
        this.genreService = genreService;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFound(final NotFoundException e) {
        return Map.of("error", "Не найден переданный параметр.");
    }

    @GetMapping
    public Collection<Genre> findAll() {
        return genreService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Genre> findOne(@PathVariable Long id) {
        Optional<Genre> genre = genreService.findOne(id);
        if (genre.isEmpty()) {
            throw new NotFoundException("Жанр не найден");
        } else {
            return genre;
        }
    }
}