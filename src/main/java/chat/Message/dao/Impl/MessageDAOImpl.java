package chat.Message.dao.Impl;

import chat.Message.dao.MessageDAO;
import chat.Message.model.Message;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * created by ewang on 2018/4/18.
 */
@Repository
public class MessageDAOImpl implements MessageDAO, RowMapper<Message> {

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

    public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Message(rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getLong("to_user_id"),
                rs.getString("content"),
                rs.getLong("session_id"),
                new Date(rs.getTimestamp("create_time").getTime()));
    }

    public List<Message> findBySession(long sessionId) {

        return null;
    }

    public long insert(long userId, long toUserId, long sessionId, String content) {
        return 0;
    }
}
