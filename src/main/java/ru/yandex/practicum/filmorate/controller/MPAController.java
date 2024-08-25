package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.service.MPAService;
import java.util.Collection;
import java.util.Optional;


@RestController
@RequestMapping("/mpa")
@Qualifier("MPADbStorage")
public class MPAController {
    private final MPAService mpaService;

    public MPAController(@Autowired MPAService mpaService) {
        this.mpaService = mpaService;
    }


    @GetMapping
    public Collection<MPA> findAll() {
        return mpaService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<MPA> findOne(@PathVariable Long id) {
        Optional<MPA> mpa = mpaService.findOne(id);
        if (mpa.isEmpty()) {
            throw new NotFoundException("Рейтинг MPA не найден");
        } else {
            return mpa;
        }
    }

}
