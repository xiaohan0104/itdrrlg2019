package com.itdr.services;

import com.itdr.common.ServiceResponse;

public interface OrderService {
    ServiceResponse creatOrder(Integer id, Integer shippingId);
    //获取订单详情
    ServiceResponse getOrderItems(Integer id, Long orderNo);

    ServiceResponse listOrder(Integer id, Integer pageSize, Integer pageNum);

    ServiceResponse countermentOrder(Integer id, Long orderN0);
}
