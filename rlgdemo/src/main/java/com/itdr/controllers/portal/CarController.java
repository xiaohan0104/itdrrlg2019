package com.itdr.controllers.portal;

import com.itdr.common.Const;
import com.itdr.common.ServiceResponse;
import com.itdr.pojo.Product;
import com.itdr.pojo.Users;
import com.itdr.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@ResponseBody
@RequestMapping("/cart/")
public class CarController {

    @Autowired
    CartService cartService;
    //购物车增加商品
    @PostMapping("add.do")
    public ServiceResponse<Product>addOne(Integer productId, Integer count, HttpSession session){

        Users users = (Users) session.getAttribute(Const.LOGINUSER);

        if (users ==null){
            return ServiceResponse.defeadetRs(Const.UsersEnum.ON_LOGIN.getCode(),
                    Const.UsersEnum.ON_LOGIN.getDesc());
        }

        return cartService.addOne(productId,count,users.getId());

    }

    //购物车增加商品
    @PostMapping("list.do")
    public ServiceResponse cartList(HttpSession session){

        Users users = (Users) session.getAttribute(Const.LOGINUSER);

        if (users ==null){
            return ServiceResponse.defeadetRs(Const.UsersEnum.ON_LOGIN.getCode(),
                    Const.UsersEnum.ON_LOGIN.getDesc());
        }
        return cartService.cartList(users.getId());

    }


    //更新商品
    @PostMapping("update.do")
    public ServiceResponse<Product>updateCart(Integer productId, Integer count, HttpSession session){

        Users users = (Users) session.getAttribute(Const.LOGINUSER);

        if (users ==null){
            return ServiceResponse.defeadetRs(Const.UsersEnum.ON_LOGIN.getCode(),
                    Const.UsersEnum.ON_LOGIN.getDesc());
        }

        return cartService.updateCart(productId,count,users.getId());

    }


    //删除商品
    @PostMapping("delete_product.do")
    public ServiceResponse<Product>deleteProduct(String productId ,HttpSession session){

        Users users = (Users) session.getAttribute(Const.LOGINUSER);

        if (users ==null){
            return ServiceResponse.defeadetRs(Const.UsersEnum.ON_LOGIN.getCode(),
                    Const.UsersEnum.ON_LOGIN.getDesc());
        }

        return cartService.deleteProduct(productId,users.getId());

    }

    //查询商品数量
    @PostMapping("get_cart_product_count.do")
    public ServiceResponse<Product>getCartProductCount(HttpSession session){

        Users users = (Users) session.getAttribute(Const.LOGINUSER);

        if (users ==null){
            return ServiceResponse.defeadetRs(Const.UsersEnum.ON_LOGIN.getCode(),
                    Const.UsersEnum.ON_LOGIN.getDesc());
        }

        return cartService.getCartProductCount(users.getId());

    }


    //购物车全选
    @PostMapping("select_all.do")
    public ServiceResponse<Product>selectAll(HttpSession session,Integer check){

        Users users = (Users) session.getAttribute(Const.LOGINUSER);

        if (users ==null){
            return ServiceResponse.defeadetRs(Const.UsersEnum.ON_LOGIN.getCode(),
                    Const.UsersEnum.ON_LOGIN.getDesc());
        }

        return cartService.selectAll(users.getId(),check,null);

    }


    //购物车quxiao全选
    @PostMapping("un_select_all.do")
    public ServiceResponse<Product>unSelectAll(HttpSession session,Integer check){

        Users users = (Users) session.getAttribute(Const.LOGINUSER);

        if (users ==null){
            return ServiceResponse.defeadetRs(Const.UsersEnum.ON_LOGIN.getCode(),
                    Const.UsersEnum.ON_LOGIN.getDesc());
        }

        return cartService.selectAll(users.getId(),check,null);

    }

    //购物车选中商品
    @PostMapping("select.do")
    public ServiceResponse<Product>select(HttpSession session,Integer check,Integer productId){

        Users users = (Users) session.getAttribute(Const.LOGINUSER);

        if (users ==null){
            return ServiceResponse.defeadetRs(Const.UsersEnum.ON_LOGIN.getCode(),
                    Const.UsersEnum.ON_LOGIN.getDesc());
        }

        return cartService.selectAll(users.getId(),check,productId);

    }

    //购物车取消选中商品
    @PostMapping("un_select.do")
    public ServiceResponse<Product>unSelect(HttpSession session,Integer check,Integer productId){

        Users users = (Users) session.getAttribute(Const.LOGINUSER);

        if (users ==null){
            return ServiceResponse.defeadetRs(Const.UsersEnum.ON_LOGIN.getCode(),
                    Const.UsersEnum.ON_LOGIN.getDesc());
        }

        return cartService.selectAll(users.getId(),check,productId);

    }
}
