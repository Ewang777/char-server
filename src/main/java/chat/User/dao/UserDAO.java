package chat.User.dao;

import chat.User.model.User;

/**
 * created by ewang on 2018/4/18.
 */
public interface UserDAO {

    long insert(String account, String password);

    User getById(long id);

    User getByAccount(String account);
}
