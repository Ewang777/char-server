package chat.Message.dao;

import chat.Message.model.Session;

/**
 * created by ewang on 2018/4/18.
 */
public interface SessionDAO {

    Session getById(long id);

    Session getByUserAndToUser(long userId, long toUserId);

    long insert(long userId, long toUserId);
}
