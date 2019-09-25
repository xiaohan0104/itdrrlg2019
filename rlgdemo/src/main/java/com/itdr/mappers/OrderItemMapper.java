package com.itdr.mappers;

import com.itdr.pojo.OrderItem;

import java.util.List;

public interface OrderItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);
    //根据订单号查询对应商品
    List<OrderItem> selectByOrderNo(Long orderno);


    int insertAll(List<OrderItem> orderItem);
    //用户id和订单号获取订单详情
    List<OrderItem> selectByOrderNoAndUid(Long orderNo, Integer uid);
}