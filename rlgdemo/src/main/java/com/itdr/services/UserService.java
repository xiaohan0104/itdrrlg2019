package com.itdr.services;

import com.itdr.common.ServiceResponse;
import com.itdr.pojo.Users;

public interface UserService {
    //用户登录
    ServiceResponse<Users> login(String username, String password);
    //用户注册
    ServiceResponse<Users> add(Users u);
    //验证是否已经注册用户名或邮箱
    ServiceResponse<Users> selectByUsernameOrEmail(String uname, String type);
    //获取用户详细信息
    ServiceResponse getInformation(Users users);
    //登录状态时更新
    ServiceResponse update_information(Users u);

    ServiceResponse<Users> forgetGetQuestion(String username);

    ServiceResponse<Users> forgetCheckAnswer(String username, String question, String answer);
}
