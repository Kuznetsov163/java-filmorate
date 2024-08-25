package ru.yandex.practicum.filmorate.mapper;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class FilmRowMapper implements RowMapper<Film> {
    private static final MPARowMapper MPARATING_ROW_MAPPER = new MPARowMapper();
    private static final GenreRowMapper GENRE_ROW_MAPPER = new GenreRowMapper();
    private static final String FIND_MPA_RATING = "SELECT * FROM MPArating where id = ?";
    private static final String FIND_GENRES = "SELECT GENRE_ID FROM FILM_GENRE where FILM_id = ?";
    private static final String FIND_GENRE = "SELECT * FROM GENRE where id = ?";
    private static final String FIND_LIKES = "SELECT USER_ID FROM LIKES where FILM_id = ?";
    protected final JdbcTemplate jdbc;

    public FilmRowMapper(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        Film film =  Film.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .mpa(jdbc.queryForObject(FIND_MPA_RATING, MPARATING_ROW_MAPPER,rs.getLong("MPArating_id"))).build();
        List<Long> genreids = jdbc.query(FIND_GENRES, (rs1, rowNum1) -> {
            Long longg = rs1.getLong("GENRE_ID");
            return longg;
        }, film.getId());
        for (Long id: genreids) {
            film.getGenres().add(jdbc.queryForObject(FIND_GENRE,GENRE_ROW_MAPPER,id));
        }
        List<Long> likeIds = jdbc.query(FIND_LIKES, (rs1, rowNum1) -> {
            Long longg = rs1.getLong("USER_ID");
            return longg;
        }, film.getId());
        for (Long id: likeIds) {
            film.getLikes().add(id);
        }
        film.setDescription(rs.getString("description"));
        film.setReleaseDate(rs.getDate("releaseDate").toLocalDate());
        film.setDuration(rs.getLong("duration"));
        return film;
    }
}
