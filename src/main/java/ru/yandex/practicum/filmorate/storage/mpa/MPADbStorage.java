package ru.yandex.practicum.filmorate.storage.mpa;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.BaseRepository;
import java.util.Collection;
import java.util.Optional;

@Repository
public class MPADbStorage extends BaseRepository<MPA> {

    private static final String FIND_MANY_QUERY = "SELECT * FROM MPArating";

    private static final String FIND_ONE_QUERY = "SELECT * FROM MPArating WHERE ID = ?";

    private static final String INSERT_QUERY = "INSER INTO MPArating (name) values (?)";

    private static final String SELECT_MAX_MPA_QUERY = "SELECT MAX(ID) FROM MPArating";

    public MPADbStorage(JdbcTemplate jdbc, RowMapper<MPA> mapper) {
        super(jdbc, mapper);
    }

    public Collection<MPA> findAll() {
        return super.findMany(FIND_MANY_QUERY);
    }

    public Optional<MPA> findOne(Long id) {
        return super.findOne(FIND_ONE_QUERY,id);
    }

}
