package ru.yandex.practicum.filmorate.mapper;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

@Component
public class FilmRowMapper implements RowMapper<Film> {
    private static final MPARowMapper MPARATING_ROW_MAPPER = new MPARowMapper();
    private static final GenreRowMapper GENRE_ROW_MAPPER = new GenreRowMapper();
    private static final String FIND_MPA_RATING = "SELECT * FROM MPArating where id = ?";
    /*private static final String FIND_GENRES = "SELECT GENRE_ID FROM FILM_GENRE where FILM_id = ?";
    private static final String FIND_GENRE = "SELECT * FROM GENRE where id = ?";
    private static final String FIND_LIKES = "SELECT USER_ID FROM LIKES where FILM_id = ?";*/
    protected final JdbcTemplate jdbc;

    public FilmRowMapper(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        Film film = new Film();
        film.setId(rs.getLong("id"));
        film.setName(rs.getString("name"));
        film.setMpa(jdbc.queryForObject(FIND_MPA_RATING, MPARATING_ROW_MAPPER,rs.getLong("MPArating_id")));
        // исправления
        List<Genre> genres = jdbc.query(
                "SELECT g.* FROM GENRE g JOIN FILM_GENRE fg ON g.id = fg.GENRE_ID WHERE fg.FILM_ID = ?",
                GENRE_ROW_MAPPER,
                film.getId());
        film.setGenres(new TreeSet<>(genres));

        List<Long> likeIds = jdbc.query(
                "SELECT l.USER_ID FROM LIKES l WHERE l.FILM_ID = ?",
                (rs1, rowNum1) -> rs1.getLong("USER_ID"),
                film.getId());
        film.setLikes(new HashSet<>(likeIds));

        film.setDescription(rs.getString("description"));
        film.setReleaseDate(rs.getDate("releaseDate").toLocalDate());
        film.setDuration(rs.getLong("duration"));
        return film;
    }
}
