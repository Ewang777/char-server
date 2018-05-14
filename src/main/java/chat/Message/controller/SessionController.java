package chat.Message.controller;

import chat.Message.dao.MessageDAO;
import chat.Message.dao.SessionDAO;
import chat.Message.model.Message;
import chat.Message.model.Session;
import chat.user.dao.UserDAO;
import chat.user.model.User;
import chat.response.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * created by ewang on 2018/4/19.
 */
@Controller
@RequestMapping("/session")
public class SessionController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private SessionDAO sessionDAO;

    @Autowired
    private MessageDAO messageDAO;

    @RequestMapping("/get")
    public ResponseWrapper findFriends(@RequestParam("userId") long currentUserId) {
        User currentUser = userDAO.getById(currentUserId);
        if (null == currentUser) {
            return new ResponseWrapper("用户不存在");
        }
        List<User> friends = userDAO.findAll().stream().filter(e -> e.getId() != currentUserId).collect(Collectors.toList());
        Map<Long, String> messageMap = new HashMap<>();
        Map<Long, Session> sessionMap = new HashMap<>();

        for (User u : friends) {
            long toUserId = u.getId();
            Session session = sessionDAO.findByUserAndToUser(currentUserId, toUserId);
            if (session != null) {
                sessionMap.put(toUserId, session);
                Message message = messageDAO.getLatestBySession(session.getId());
                if (message != null) {
                    messageMap.put(toUserId, message.getContent());
                }
            }
        }
        return new ResponseWrapper()
                .addObject(friends, "userList")
                .addObject(messageMap, "messageMap")
                .addObject(sessionMap, "sessionMap");
    }

    @RequestMapping("/message/get")
    public ResponseWrapper getMessage(@RequestParam("userId") long userId,
                                      @RequestParam("toUserId") long toUserId) {
        Session session = findOrCreateSession(userId, toUserId);
        List<Message> messages = messageDAO.findBySession(session.getId());
        int oldUnread = session.getUnread();
        if (oldUnread != 0) {
            //清除未读消息
            sessionDAO.updateUnread(session.getId(), oldUnread, 0);
        }
        return new ResponseWrapper().addObject(messages, "messageList");
    }

    @RequestMapping("/message/send")
    public ResponseWrapper sendMessage(@RequestParam("userId") long userId,
                                       @RequestParam("toUserId") long toUserId,
                                       @RequestParam("content") String content) {
        Session session = findOrCreateSession(userId, toUserId);
        Session toSession = findOrCreateSession(toUserId, userId);

        long messageId = messageDAO.insert(userId, toUserId, session.getId(), content);
        Message message = messageDAO.getById(messageId);
        sessionDAO.updateTime(session.getId(), message.getCreateTime());

        if (!Objects.equals(userId, toUserId)) {
            long toSessionId = toSession.getId();
            int oldUnread = toSession.getUnread();
            messageDAO.insert(userId, toUserId, toSessionId, content);
            sessionDAO.updateTime(toSessionId, message.getCreateTime());
            sessionDAO.updateUnread(toSessionId, oldUnread, oldUnread + 1);
        }
        return new ResponseWrapper().addObject(message, "message");
    }

    @RequestMapping("/clear/unread")
    public ResponseWrapper clearUnread(@RequestParam("userId") long userId,
                                       @RequestParam("toUserId") long toUserId) {
        Session session = sessionDAO.findByUserAndToUser(userId, toUserId);
        if(session!=null) {
            int result = sessionDAO.updateUnread(session.getId(), session.getUnread(), 0);
            if (result>0){
                return new ResponseWrapper();
            }
        }
        return new ResponseWrapper("清理未读消息失败");
    }

    Session findOrCreateSession(long userId, long toUserId) {
        Session session = sessionDAO.findByUserAndToUser(userId, toUserId);
        if (session == null) {
            long sessionId = sessionDAO.insert(userId, toUserId);
            session = sessionDAO.getById(sessionId);
        }
        return session;
    }
}
