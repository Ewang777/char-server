package chat.Message.dao;

/**
 * created by ewang on 2018/4/18.
 */
public interface SessionDAO {
    long insert(long authorId, long toUserId);
}
