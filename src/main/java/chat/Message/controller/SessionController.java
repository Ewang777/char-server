package chat.Message.controller;

import chat.Message.dao.MessageDAO;
import chat.Message.dao.SessionDAO;
import chat.Message.model.Message;
import chat.Message.model.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * created by ewang on 2018/4/19.
 */
@Controller
@RequestMapping("/session")
public class SessionController {

    @Autowired
    private SessionDAO sessionDAO;

    @Autowired
    private MessageDAO messageDAO;

    @RequestMapping("/message/get")
    public List<Message> getMessage(@RequestParam("userId") long userId,
                                    @RequestParam("toUserId") long toUserId) {
        Session session = getSession(userId, toUserId);
        return messageDAO.findBySession(session.getId());
    }

    @RequestMapping("/message/send")
    public Message sendMessage(@RequestParam("userId") long userId,
                               @RequestParam("toUserId") long toUserId,
                               @RequestParam("content") String content) {
        Session session = getSession(userId, toUserId);
        Session toSession = getSession(toUserId, userId);
        messageDAO.insert(userId, toUserId, toSession.getId(), content);
        long messageId = messageDAO.insert(userId, toUserId, session.getId(), content);
        Message message = messageDAO.getById(messageId);
        return message;
    }

    Session getSession(long userId, long toUserId) {
        Session session = sessionDAO.getByUserAndToUser(userId, toUserId);
        if (session == null) {
            long sessionId = sessionDAO.insert(userId, toUserId);
            session = sessionDAO.getById(sessionId);
        }
        return session;
    }
}
