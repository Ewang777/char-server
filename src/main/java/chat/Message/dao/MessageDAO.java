package chat.Message.dao;

import chat.Message.model.Message;

import java.util.List;

/**
 * created by ewang on 2018/4/18.
 */
public interface MessageDAO {

    Message getById(long id);

    List<Message> findBySession(long sessionId);

    Message getLatestBySession(long sessionId);

    long insert(long userId, long toUserId, long sessionId, String content);

}
