package com.itdr.mappers;

import com.itdr.pojo.Cart;
import com.itdr.pojo.vo.CartProductVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {

//根据用户ID查询所有
     List<Cart> selectListCart(Integer uid);


    int deleteByPrimaryKey(@Param("productId")List<String> productId,@Param("id")Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);


    Cart selectByuIdAndPid(@Param("userId") Integer userId,
                          @Param("productId")Integer productId);

    int selectaByUidCheck(@Param("uid")Integer uid,@Param("check")Integer check);

    List<CartProductVo> cartList(@Param("id")Integer id);

    int getCartProductCount(@Param("id")Integer id);

    int selectAll(@Param("id")Integer id,@Param("check")Integer check,@Param("productId")Integer productId);

    List<Cart> selectByUidAll(Integer uid);

    int deleteAllByidAndUid(@Param("cartList") List<Cart> cartList,@Param("uid") Integer uid);
}