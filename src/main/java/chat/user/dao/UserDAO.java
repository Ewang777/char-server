package chat.user.dao;

import chat.user.model.User;

import java.util.List;

/**
 * created by ewang on 2018/4/18.
 */
public interface UserDAO {

    long insert(String account, String password);

    User getById(long id);

    User getByAccount(String account);

    List<User> findAll();
}
