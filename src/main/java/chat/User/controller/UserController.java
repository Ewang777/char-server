package chat.User.controller;

import chat.Message.dao.MessageDAO;
import chat.Message.dao.SessionDAO;
import chat.Message.model.Message;
import chat.Message.model.Session;
import chat.User.dao.UserDAO;
import chat.User.model.User;
import chat.response.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * created by ewang on 2018/4/18.
 */
@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private MessageDAO messageDAO;

    @Autowired
    private SessionDAO sessionDAO;

    @RequestMapping
    public void init(HttpServletResponse response) throws IOException {
        response.getWriter().print("server is here!");
    }

    @RequestMapping(value = "/user/reg", method = RequestMethod.POST)
    public ResponseWrapper reg(@RequestParam("account") String account,
                               @RequestParam("password") String pwd) {
        account = account.trim();
        pwd = pwd.trim();
        if (account.isEmpty() || null == account || pwd.isEmpty() || null == pwd) {
            return new ResponseWrapper(" 用户名或密码不得为空");
        }
        User exist = userDAO.getByAccount(account);
        if (exist != null) {
            return new ResponseWrapper("用户名已存在");
        }
        long userId = userDAO.insert(account, pwd);
        exist = userDAO.getById(userId);
        return new ResponseWrapper().addObject(exist, "user");
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public ResponseWrapper login(@RequestParam("account") String account,
                                 @RequestParam("password") String pwd) {
        account = account.trim();
        pwd = pwd.trim();
        if (account.isEmpty() || null == account || pwd.isEmpty() || null == pwd) {
            return new ResponseWrapper("用户名或密码不得为空");
        }
        User exist = userDAO.getByAccount(account);
        if (exist == null) {
            return new ResponseWrapper("用户名不存在");
        }
        if (!exist.getPassword().equals(pwd)) {
            return new ResponseWrapper("密码错误");
        }
        return new ResponseWrapper().addObject(exist, "user");

    }

    @RequestMapping("/user/find")
    public ResponseWrapper findFriends(@RequestParam("userId") long currentUserId) {
        User currentUser = userDAO.getById(currentUserId);
        if (null == currentUser) {
            return new ResponseWrapper("用户不存在在");
        }
        List<User> friends = userDAO.findAll().stream().filter(e -> e.getId() != currentUserId).collect(Collectors.toList());
        Map<Long, Message> messageMap = new HashMap<>();

        for (User u : friends) {
            long userId = u.getId();
            Message message = getLatestMessage(currentUserId, userId);
            if (message != null) {
                messageMap.put(userId, message);
            }
        }
        return new ResponseWrapper()
                .addObject(friends, "userList")
                .addObject(messageMap, "messageMap");
    }

    Message getLatestMessage(long userId, long toUserId) {
        Session session = sessionDAO.getByUserAndToUser(userId, toUserId);
        if (session == null) {
            return null;
        }
        Message message = messageDAO.getLatestBySession(session.getId());
        return message;
    }
}
