package com.itdr.controllers.portal;

import com.itdr.common.Const;
import com.itdr.common.ServiceResponse;
import com.itdr.pojo.Users;
import com.itdr.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
@ResponseBody
public class UserController {
    @Autowired
    UserService userService;

    //用户登录
    @PostMapping("/login.do")
    public ServiceResponse<Users> login(String username, String password, HttpSession httpSession) {


        ServiceResponse<Users> sr = userService.login(username, password);

        if (sr.issuccerd()) {
            Users u = sr.getData();
            Users u2 = new Users();
            u2.setId(u.getId());
            u2.setUsername(u.getUsername());
            u2.setEmail(u.getEmail());
            u2.setPhone(u.getPhone());
            u2.setCreateTime(u.getCreateTime());
            u2.setUpdateTime(u.getUpdateTime());
            u2.setPassword("");

            httpSession.setAttribute(Const.LOGINUSER, u);

            sr.setData(u2);
        }
        return sr;

    }

    //用户注册
    @PostMapping("/add.do")
    public ServiceResponse<Users> add(Users u) {
        ServiceResponse<Users> sr = userService.add(u);
        return sr;
    }

    //验证是否已经注册用户名或邮箱
    @PostMapping("/selectunameemail.do")
    public ServiceResponse<Users> selectByUsernameOrEmail(String str, String type) {
        ServiceResponse<Users> sr = userService.selectByUsernameOrEmail(str, type);
        return sr;
    }

    //获取登录用户信息
    @PostMapping("/get_user_info.do")
    public ServiceResponse<Users> getUserInfo(HttpSession session) {
        Users users = (Users) session.getAttribute(Const.LOGINUSER);
        if (users == null) {
            return ServiceResponse.defeadetRs(Const.UsersEnum.ON_LOGIN.getCode(), Const.UsersEnum.ON_LOGIN.getDesc());
        } else {
            return ServiceResponse.successRs(users);
        }

    }

    //退出登录
    @PostMapping("logout.do")
    public ServiceResponse<Users> logout(HttpSession session) {

        session.removeAttribute(Const.LOGINUSER);
        return ServiceResponse.successRs(Const.LOGOUT);
    }

    //获取用户详细信息
    @PostMapping("get_information.do")
    public ServiceResponse<Users> getInformation(HttpSession session) {
        Users users = (Users)session.getAttribute(Const.LOGINUSER);
        if (users == null) {
            return ServiceResponse.defeadetRs(Const.UsersEnum.ON_LOGIN.getCode(), Const.UsersEnum.ON_LOGIN.getDesc());
        } else {
            ServiceResponse sr = userService.getInformation(users);
            return sr;
        }
    }

    //登录状态更改个人信息
    @PostMapping("update_information.do")
    public ServiceResponse<Users> updateInformation(Users u,HttpSession session) {
        Users users = (Users)session.getAttribute(Const.LOGINUSER);
        if (users == null) {
            return ServiceResponse.defeadetRs(Const.UsersEnum.ON_LOGIN.getCode(), Const.UsersEnum.ON_LOGIN.getDesc());
        } else {

            u.setId(users.getId());
            u.setUsername(users.getUsername());
            ServiceResponse sr = userService.update_information(u);
            session.setAttribute(Const.LOGINUSER,u);
            return sr;
        }
    }


    //忘记密码
    @PostMapping("forget_get_question.do")
    public ServiceResponse<Users> forgetGetQuestion(String username) {

         return userService.forgetGetQuestion(username);

    }

    //修改问题答案
    @PostMapping("forget_check_answer.do")
    public ServiceResponse<Users> forgetCheckAnswer(String username,String question,String answer) {

        return userService.forgetCheckAnswer(username,question,answer);
    }

}
