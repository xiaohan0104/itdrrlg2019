package com.itdr.services;

import com.itdr.common.ServiceResponse;
import com.itdr.pojo.Product;

import javax.servlet.http.HttpSession;

public interface CartService {
    //购物车增加商品
    ServiceResponse<Product> addOne(Integer productId, Integer count,Integer userId);
//购物车列表
    ServiceResponse<Product> cartList(Integer id);
//更新商品
    ServiceResponse<Product> updateCart(Integer productId, Integer count, Integer id);
//一处商品
    ServiceResponse<Product> deleteProduct(String productId, Integer id);
    //查询商品数量
    ServiceResponse<Product> getCartProductCount(Integer id);
    //购物车全选
    ServiceResponse selectAll(Integer id,Integer check,Integer productId);
}
