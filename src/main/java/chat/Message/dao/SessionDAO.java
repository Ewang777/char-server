package chat.Message.dao;

import chat.Message.model.Session;

/**
 * created by ewang on 2018/4/18.
 */
public interface SessionDAO {

    Session getById(long id);

    Session getByAuthorAndToUser(long authorId, long toUserId);

    long insert(long authorId, long toUserId);
}
