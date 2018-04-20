package chat.User.dao.Impl;

import chat.Helper.DBHelper;
import chat.User.dao.UserDAO;
import chat.User.model.User;
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
public class UserDAOImpl implements UserDAO, RowMapper<User> {

    private NamedParameterJdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        jdbcTemplate = new NamedParameterJdbcTemplate(DBHelper.getDataSource());
    }


    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(rs.getLong("id"),
                rs.getString("account"),
                rs.getString("password"),
                rs.getString("username"),
                new Date(rs.getTimestamp("create_time").getTime()));
    }

    public long insert(String account, String password) {
        String sql = "insert into user (account, password, create_time) values (:account, :password, now())";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("account", account);
        parameterSource.addValue("password", password);
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, parameterSource, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public User getById(long id) {
        String sql = "select * from user where id = :id";
        try {
            return jdbcTemplate.queryForObject(sql, Collections.singletonMap("id", id), this);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    public User getByAccount(String account) {
        String sql = "select * from user where account = :account";
        try {
            return jdbcTemplate.queryForObject(sql, Collections.singletonMap("account", account), this);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }
}
