package chat.Message.model;

import java.util.Date;

/**
 * created by ewang on 2018/4/18.
 */
public class Session {

    private final long id;

    private final long authorId;

    private final long toUserId;

    private final Date createTime;

    public Session(long id, long authorId, long toUserId, Date createTime) {
        this.id = id;
        this.authorId = authorId;
        this.toUserId = toUserId;
        this.createTime = createTime;
    }

    public long getId() {
        return id;
    }

    public long getAuthorId() {
        return authorId;
    }

    public long getToUserId() {
        return toUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }
}
