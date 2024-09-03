package ru.yandex.practicum.filmorate.storage.genre;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.BaseRepository;

import java.util.Collection;
import java.util.Optional;

@Repository
public class GenreDbStorage extends BaseRepository<Genre> {

    private static final String FIND_MANY = "SELECT * FROM GENRE";

    private static final String FIND_ONE_QUERY = "SELECT * FROM GENRE WHERE ID = ?";

    public GenreDbStorage(JdbcTemplate jdbc, RowMapper<Genre> mapper) {
        super(jdbc, mapper);
    }

    public Collection<Genre> findAll() {
        return super.findMany(FIND_MANY);
    }

    public Optional<Genre> findOne(Long id) {
        return super.findOne(FIND_ONE_QUERY,id);
    }
}
