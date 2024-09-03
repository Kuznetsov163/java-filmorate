package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.mpa.MPADbStorage;

import java.util.Collection;
import java.util.Optional;

@Service
public class MPAService {
    private final MPADbStorage mpaDbStorage;

    public MPAService(@Autowired MPADbStorage mpaDbStorage) {
        this.mpaDbStorage = mpaDbStorage;
    }


    public Collection<MPA> findAll() {
        return mpaDbStorage.findAll();
    }

    public Optional<MPA> findOne(Long id) {
        return mpaDbStorage.findOne(id);
    }

}