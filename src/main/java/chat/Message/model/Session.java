package chat.Message.model;

import java.util.Date;

/**
 * created by ewang on 2018/4/18.
 */
public class Session {

    private final long id;

    private final long userId;

    private final long toUserId;

    private final Date createTime;

    public Session(long id, long userId, long toUserId, Date createTime) {
        this.id = id;
        this.userId = userId;
        this.toUserId = toUserId;
        this.createTime = createTime;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public long getToUserId() {
        return toUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }
}
