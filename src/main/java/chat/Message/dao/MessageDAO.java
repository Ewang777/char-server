package chat.Message.dao;

import chat.Message.model.Message;

import java.util.List;

/**
 * created by ewang on 2018/4/18.
 */
public interface MessageDAO {

    List<Message> findBySession(long sessionId);

    long insert(long userId, long toUserId, long sessionId, String content);

}