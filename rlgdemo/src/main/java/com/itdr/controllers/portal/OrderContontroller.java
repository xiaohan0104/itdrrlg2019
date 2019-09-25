package com.itdr.controllers.portal;

import com.itdr.common.Const;
import com.itdr.common.ServiceResponse;
import com.itdr.pojo.Users;
import com.itdr.services.OrderService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@ResponseBody
@RequestMapping("/order/")
public class OrderContontroller {
    @Autowired
    OrderService orderService;

    //创建订单
    @RequestMapping("creat.do")
    public ServiceResponse creatOrder(HttpSession session,Integer shippingId){
        Users users = (Users) session.getAttribute(Const.LOGINUSER);

        if (users == null){
            return ServiceResponse.defeadetRs(Const.UsersEnum.ON_LOGIN.getCode(),Const.UsersEnum.ON_LOGIN.getDesc());
        }
        return orderService.creatOrder(users.getId(),shippingId);
    }

    //获取订单详情
    @RequestMapping("get_orderItems.do")
    public ServiceResponse getOrderItems(HttpSession session, @RequestParam (value = "orderNo" ,required = false) Long orderNo){
        Users users = (Users) session.getAttribute(Const.LOGINUSER);

        if (users == null){
            return ServiceResponse.defeadetRs(Const.UsersEnum.ON_LOGIN.getCode(),Const.UsersEnum.ON_LOGIN.getDesc());
        }
        return orderService.getOrderItems(users.getId(),orderNo);
    }
}
