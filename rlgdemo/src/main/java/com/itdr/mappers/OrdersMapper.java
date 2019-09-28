package com.itdr.mappers;

import com.itdr.pojo.Orders;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrdersMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Orders record);

    int insertSelective(Orders record);

    Orders selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Orders record);

    int updateByPrimaryKey(Orders record);
//出巡订单是否存在
    Orders selectByOrderNo(Long orderno);
//    根据订单和用户id查询
    int selectByOrderNoAndUid(@Param("orderno") Long orderno, @Param("id") Integer id);

    List<Orders> selectByUid();

    int updateToStatus(Orders orders);
}