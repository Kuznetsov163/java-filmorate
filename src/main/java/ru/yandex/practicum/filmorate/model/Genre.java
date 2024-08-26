package ru.yandex.practicum.filmorate.model;

import lombok.Data;

@Data
public class Genre implements Comparable<Genre> {
    Long id;
    String name;

    @Override
    public int compareTo(Genre o) {
        return Integer.parseInt(String.valueOf(this.id - o.getId()));
    }
}
