package chat.Message.dao.Impl;

import chat.Helper.DBHelper;
import chat.Message.dao.SessionDAO;
import chat.Message.model.Session;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
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
        jdbcTemplate = new NamedParameterJdbcTemplate(DBHelper.getDataSource());
    }

    public Session mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Session(rs.getLong("id"),
                rs.getLong("user_id"),
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
    public Session getByUserAndToUser(long userId, long toUserId) {
        String sql = "select * from session where user_id = :userId and to_user_id = :toUserId";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("userId", userId);
        parameterSource.addValue("toUserId", toUserId);
        try {
            return jdbcTemplate.queryForObject(sql, parameterSource, this);
        } catch (IncorrectResultSizeDataAccessException r) {
            return null;
        }
    }

    public long insert(long userId, long toUserId) {
        String sql = "insert into session (user_id, to_user_id, create_time) values (:userId, :toUserId, now())";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("userId", userId);
        parameterSource.addValue("toUserId", toUserId);
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, parameterSource, keyHolder);
        return keyHolder.getKey().longValue();
    }
}
