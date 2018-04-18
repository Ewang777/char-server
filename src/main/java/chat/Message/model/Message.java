package chat.Message.model;

import java.util.Date;

/**
 * created by ewang on 2018/4/18.
 */
public class Message {

    private final long id;

    private final long userId;

    private final long toUserId;

    private final String content;

    private final Date createTime;

    public Message(long id, long userId, long toUserId, String content, Date createTime) {
        this.id = id;
        this.userId = userId;
        this.toUserId = userId;
        this.content = content;
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

    public String getContent() {
        return content;
    }

    public Date getCreateTime() {
        return createTime;
    }
}
