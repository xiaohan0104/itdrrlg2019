package com.itdr.services.impl;

import com.itdr.common.Const;
import com.itdr.common.ServiceResponse;
import com.itdr.mappers.CartMapper;
import com.itdr.mappers.ProductMapper;
import com.itdr.pojo.Cart;
import com.itdr.pojo.Product;
import com.itdr.pojo.vo.CartProductVo;
import com.itdr.pojo.vo.CartVo;
import com.itdr.services.CartService;
import com.itdr.util.BigDecimalUtils;
import com.itdr.util.PoToVoUtilsO;
import com.itdr.util.PropertiesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CartServiceimpl implements CartService {

    @Autowired
    CartMapper cartMapper;

    @Autowired
    ProductMapper productMapper;

    //购物车添加商品
    @Override
    public ServiceResponse<Product> addOne(Integer productId, Integer count, Integer userId) {


        //参数非空判断
        if (productId == null || productId <= 0 || count == null || count <= 0) {
            return ServiceResponse.defeadetRs(Const.FEIFACANSHU);
        }
        //想购物车种存储数据


        //如果有这条购物信息就是更新数量没有就是增加
        Cart cart1 = cartMapper.selectByuIdAndPid(userId, productId);
        if (cart1 != null) {
            cart1.setQuantity(cart1.getQuantity() + count);
            //更新数据
            int updateNum = cartMapper.updateByPrimaryKeySelective(cart1);
        } else {
            //新建数据
            Cart cart = new Cart();
            cart.setUserId(userId);
            cart.setProductId(productId);
            cart.setQuantity(count);
            int insert = cartMapper.insert(cart);
        }
CartVo cartVo =  getCartProductVo(userId);

        return ServiceResponse.successRs(cartVo);
    }
//获得购物车列表
    @Override
    public ServiceResponse cartList(Integer id) {

        return ServiceResponse.successRs(this.getCartProductVo(id));
    }



//更新购物车商品
    @Override
    public ServiceResponse<Product> updateCart(Integer productId, Integer count, Integer id) {
        //参数非空判断
        if (productId == null || productId <= 0 || count == null || count <= 0) {
            return ServiceResponse.defeadetRs(Const.FEIFACANSHU);
        }

        //如果有这条购物信息就是更新数量没有就是增加
        Cart cart1 = cartMapper.selectByuIdAndPid(id, productId);
        //更新数据
            cart1.setQuantity( count);
            int updateNum = cartMapper.updateByPrimaryKeySelective(cart1);


        return ServiceResponse.successRs(cartList(id));
    }

    @Override
    public ServiceResponse<Product> deleteProduct(String productId, Integer id) {
if (productId == null || "".equals(productId)){
    return ServiceResponse.defeadetRs(Const.FEIFACANSHU);
}

        String[] split = productId.split(",");
        List<String> strings = Arrays.asList(split);
        int i = cartMapper.deleteByPrimaryKey(strings,id);
        return ServiceResponse.successRs(Const.SUCESS,i);
    }

    @Override
    public ServiceResponse<Product> getCartProductCount(Integer id) {
       int num = cartMapper.getCartProductCount(id);
        return ServiceResponse.successRs(Const.SUCESS,num);
    }
//全选
    @Override
    public ServiceResponse selectAll(Integer id,Integer check,Integer productId) {
        int  num =cartMapper.selectAll(id,check,productId);
        return cartList(id);
    }


    //创建返回VO类
    private CartVo getCartProductVo(Integer uid) {
        BigDecimal bigDecimal = new BigDecimal("0");
        //创建封装类
        CartVo cartVo = new CartVo();
        ArrayList<CartProductVo> cartProductVoArrayList = new ArrayList<CartProductVo>();
        //根据用户ID出巡该用户的所有购物车信息
        List<Cart> listCart = cartMapper.selectListCart(uid);
        //从购物车信息集合中拿出每一条数据，根据商品id查询需要的山沟你
        if (listCart.size() != 0) {
            for (Cart cart : listCart) {
                //根据购物车中的用户ID出巡商品信息
                Product product = productMapper.selectById(cart.getProductId(), 0, 0, 0);

                 //用工具类吧查询的购物车用户信息和商品信息进行封装
                CartProductVo cartProductVo = PoToVoUtilsO.addCartProductVo(cart, product);

               //购物车更新有效库存
                Cart cartForQuantity = new Cart();
                cartForQuantity.setId(cart.getId());
                cartForQuantity.setQuantity(cartProductVo.getQuantity());
                cartMapper.updateByPrimaryKeySelective(cartForQuantity);

                //购物车总价
                if (cart.getChecked() == Const.CHECK) {
                    bigDecimal = BigDecimalUtils.add(bigDecimal.doubleValue(), cartProductVo.getProductTotalPrice().doubleValue());

                }
                cartProductVoArrayList.add(cartProductVo);


            }
        }

        cartVo.setCartProductVos(cartProductVoArrayList);
        cartVo.setImageHost(PropertiesUtils.getValue("imageHost"));
        cartVo.setAllChecked(this.CheclAll(uid));
        cartVo.setCartTotalPrice(bigDecimal);
        return cartVo;

    }
    //判断用户购物车是否全选
    private boolean CheclAll(Integer uid){
        int selectaByUidCheck = cartMapper.selectaByUidCheck(uid,Const.UNCHECK);
        if (selectaByUidCheck == 0){
            return true;
        }else {
            return false;

        }
    }

}

