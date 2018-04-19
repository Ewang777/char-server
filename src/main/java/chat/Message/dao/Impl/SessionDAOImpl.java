package chat.Message.dao.Impl;

import chat.Message.dao.SessionDAO;
import chat.Message.model.Session;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;

/**
 * created by ewang on 2018/4/18.
 */
@Repository
public class SessionDAOImpl implements SessionDAO, RowMapper<Session> {

    private NamedParameterJdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1/chat");
        dataSource.setUsername("root");
        dataSource.setPassword("0530");
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public Session mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Session(rs.getLong("id"),
                rs.getLong("author_id"),
                rs.getLong("to_user_id"),
                new Date(rs.getTimestamp("create_time").getTime()));
    }

    @Override
    public Session getById(long id) {
        String sql = "select * from session where id = :id";
        try {
            return jdbcTemplate.queryForObject(sql, Collections.singletonMap("id", id), this);
        } catch (IncorrectResultSizeDataAccessException r) {
            return null;
        }
    }

    @Override
    public Session getByAuthorAndToUser(long authorId, long toUserId) {
        String sql = "select * from session where author_id = :authorId and to_user_id = :toUserId";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("authorId", authorId);
        parameterSource.addValue("toUserId", toUserId);
        try {
            return jdbcTemplate.queryForObject(sql, parameterSource, this);
        } catch (IncorrectResultSizeDataAccessException r) {
            return null;
        }
    }

    public long insert(long authorId, long toUserId) {
        return 0;
    }
}
