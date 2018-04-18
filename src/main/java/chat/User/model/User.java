package chat.User.model;

import java.util.Date;

/**
 * created by ewang on 2018/4/18.
 */
public class User {

    private final long id;

    private final String account;

    private final String password;

    private final String username;

    private final Date createTime;

    public User(long id, String account, String password, String username,Date createTime) {
        this.id = id;
        this.account = account;
        this.password = password;
        this.username = username;
        this.createTime=createTime;
    }

    public long getId() {
        return id;
    }

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public Date getCreateTime() {
        return createTime;
    }
}
