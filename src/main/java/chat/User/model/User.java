package chat.User.model;

/**
 * created by ewang on 2018/4/18.
 */
public class User {

    private final long id;

    private final String account;

    private final String password;

    private final String username;

    public User(long id, String account, String password, String username) {
        this.id = id;
        this.account = account;
        this.password = password;
        this.username = username;
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
}
