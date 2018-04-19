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

    @RequestMapping("/message")
    public List<Message> getMessage(@RequestParam("authorId") long authorId,
                                    @RequestParam("toUserId") long toUserId) {
        Session session = getSession(authorId, toUserId);
        return messageDAO.findBySession(session.getId());
    }

    public void sendMessage(@RequestParam("userId") long userId,
                            @RequestParam("toUserId") long toUserId){

    }

    Session getSession(long authorId, long toUserId) {
        Session session = sessionDAO.getByAuthorAndToUser(authorId, toUserId);
        if (session == null) {
            long sessionId = sessionDAO.insert(authorId, toUserId);
            session = sessionDAO.getById(sessionId);
        }
        return session;
    }
}
