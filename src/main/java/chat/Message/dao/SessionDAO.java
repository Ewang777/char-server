package chat.Message.dao;

import chat.Message.model.Session;

import java.util.Date;

/**
 * created by ewang on 2018/4/18.
 */
public interface SessionDAO {

    Session getById(long id);

    Session findByUserAndToUser(long userId, long toUserId);

    long insert(long userId, long toUserId);

    int updateUnread(long sessionId, int oldData, int newData);

    int updateTime(long sessionId, Date updateTime);
}
