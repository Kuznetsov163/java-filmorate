package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreDbStorage;

import java.util.Collection;
import java.util.Optional;

@Service
public class GenreService {
    private final GenreDbStorage genreDbStorage;

    public GenreService(@Autowired GenreDbStorage genreDbStorage) {
        this.genreDbStorage = genreDbStorage;
    }


    public Collection<Genre> findAll() {
        return genreDbStorage.findAll();
    }

    public Optional<Genre> findOne(Long id) {
        return genreDbStorage.findOne(id);
    }

}
