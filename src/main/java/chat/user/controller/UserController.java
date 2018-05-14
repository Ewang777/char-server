package chat.user.controller;

import chat.user.dao.UserDAO;
import chat.user.model.User;
import chat.response.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * created by ewang on 2018/4/18.
 */
@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserDAO userDAO;

    @RequestMapping
    public void init(HttpServletResponse response) throws IOException {
        response.getWriter().print("server is here!");
        //项目启动时会初始化这个东西三次
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

}
