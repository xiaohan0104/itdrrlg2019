package com.itdr.mappers;

import com.itdr.pojo.Users;
import org.apache.ibatis.annotations.Param;

public interface UsersMapper {
    int deleteByPrimaryKey(Integer id);
    //用户注册
    int insert(Users record);

    int insertSelective(Users record);

    Users selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);

    Users selectByUsernameAndApassword(@Param("username") String username,@Param("password") String password);
    //验证是否已经注册用户名或邮箱
    int selectByUsernameOrEmail(@Param("str") String str,@Param("type") String type);

    String selectByUserName(String username);
}