package chat.Helper;

import com.zaxxer.hikari.HikariDataSource;

/**
 * created by ewang on 2018/4/19.
 */
public class DBHelper {
    public static HikariDataSource getDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1/chat");
        dataSource.setUsername("root");
        dataSource.setPassword("0530");
        return dataSource;
    }
}
