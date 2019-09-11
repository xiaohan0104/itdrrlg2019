package com.itdr.services.impl;

import com.itdr.common.Const;
import com.itdr.common.ServiceResponse;
import com.itdr.mappers.UsersMapper;
import com.itdr.pojo.Users;
import com.itdr.services.UserService;
import com.itdr.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UsersMapper usersMapper;
    //用户登录
    @Override
    public ServiceResponse<Users> login(String username, String password) {

        if(username == null||username.equals("")){
            return ServiceResponse.defeadetRs("用户名不能为空");
        }
        if(password == null||password.equals("")){
            return ServiceResponse.defeadetRs("密码不能为空");
        }

        //根据用户名查找是否存在该用户

        int q = usersMapper.selectByUsernameOrEmail(username, "username");

        if (q<=0){
            return ServiceResponse.defeadetRs("用户名不存在");
        }
        //根据用户名和密码查询用户是否粗在

        String md5password = MD5Utils.getMD5Code(password);

       Users u = usersMapper.selectByUsernameAndApassword(username,md5password);
if (u == null){
    return ServiceResponse.defeadetRs("用户名或密码错误");
}

//给前端返回数据



//返回数据
ServiceResponse sr = ServiceResponse.successRs(u);
        return sr;
    }
    //用户注册
    @Override
    public ServiceResponse<Users> add(Users u) {

        if (u.getUsername() == null||u.getUsername().equals("")){
            return ServiceResponse.defeadetRs("用户名不能为空");
        }

        if (u.getPassword() == null||u.getPassword().equals("")){
            return ServiceResponse.defeadetRs("密码不能为空");
        }


        //检出用户性是否存在

        int w = usersMapper.selectByUsernameOrEmail(u.getUsername(), "username");

        if (w>0){
            return ServiceResponse.defeadetRs("用户名已存在");
        }
u.setPassword(MD5Utils.getMD5Code(u.getPassword()));
        int i = usersMapper.insert(u);
        if (i<=0){
            return ServiceResponse.defeadetRs("用户注册失败");
        }
        return ServiceResponse.successRs(200,null,"用户注册成功");
    }
    //验证是否已经注册用户名或邮箱
    @Override
    public ServiceResponse<Users> selectByUsernameOrEmail(String str, String type) {


        if(str == null||str.equals("")){
            return ServiceResponse.defeadetRs("用户名不能为空");
        }
        if(type == null||type.equals("")){
            return ServiceResponse.defeadetRs("参数类型不能为空");
        }


       int num= usersMapper.selectByUsernameOrEmail(str,type);
        if (num>0&&type.equals("username")){
            return ServiceResponse.defeadetRs("用户已注册");
        }
        if (num>0&&type.equals("email")){
            return ServiceResponse.defeadetRs("邮箱已注册");
        }
        return ServiceResponse.successRs(200,null,"校验成功");
    }
    //获取用户详细信息
    @Override
    public ServiceResponse getInformation(Users users) {
        Users users1 = usersMapper.selectByPrimaryKey(users.getId());
        if (users1 == null){
            return ServiceResponse.defeadetRs(Const.NOEONR);
        }
        users1.setPassword("");

        return ServiceResponse.successRs(users1);
    }

    @Override
    public ServiceResponse update_information(Users u) {
        int i = usersMapper.updateByPrimaryKeySelective(u);
        if (i<=0){
            return ServiceResponse.defeadetRs("更新失败");
        }else {
            return ServiceResponse.successRs("更新成功");
        }

    }

    @Override
    public ServiceResponse<Users> forgetGetQuestion(String username) {

        if (username == null||username.equals("")){
            return ServiceResponse.defeadetRs("用户名buneng 为空");
        }
        int i = usersMapper.selectByUsernameOrEmail(username, Const.USERNAME);

        if (i<=0){
            return ServiceResponse.defeadetRs("用户名不存在");
        }

        String question = usersMapper.selectByUserName(username);
        if (question == null||"".equals("")){
            return ServiceResponse.defeadetRs("用户未设置没密保问题");
        }
        return ServiceResponse.successRs(question);

    }

    @Override
    public ServiceResponse<Users> forgetCheckAnswer(String username, String question, String answer) {

        if (username == null||username.equals("")){
            return ServiceResponse.defeadetRs("用户名不能为空");
        }
        if (question == null||question.equals("")){
            return ServiceResponse.defeadetRs("问题不能为空");
        }
        if (answer == null||answer.equals("")){
            return ServiceResponse.defeadetRs("答案不能为空");
        }

        return null;
    }
}
