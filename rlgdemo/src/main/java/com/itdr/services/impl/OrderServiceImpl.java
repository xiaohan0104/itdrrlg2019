package com.itdr.services.impl;

import com.itdr.common.Const;
import com.itdr.common.ServiceResponse;
import com.itdr.mappers.*;
import com.itdr.pojo.*;
import com.itdr.pojo.vo.OrderItemVo;
import com.itdr.pojo.vo.OrderVo;
import com.itdr.pojo.vo.ShippingVo;
import com.itdr.services.OrderService;
import com.itdr.util.BigDecimalUtils;
import com.itdr.util.PoToVoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrdersMapper ordersMapper;
    @Autowired
    CartMapper cartMapper;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    OrderItemMapper orderItemMapper;
    @Autowired
    ShippingMapper shippingMapper;


    //创建订单
    @Override
    public ServiceResponse creatOrder(Integer uid, Integer shippingid) {
        //参数判断
        if (shippingid == null || shippingid <= 0) {
            return ServiceResponse.defeadetRs(Const.FEIFACANSHU);
        }
        //获取用户购物车中选中的数据
        List<Cart> cartList = cartMapper.selectByUidAll(uid);
        List<Product> productList = new ArrayList<Product>();

        if (cartList.size() == 0) {
            return ServiceResponse.defeadetRs("至少选中一件商品");
        }

        //获取用户商品地址
Shipping shipping=shippingMapper.selectByIdAndUid(shippingid,uid);
        //订单总价
        BigDecimal payment = new BigDecimal(0);
        for (Cart cart : cartList) {
            //判断商品是否失效
            Integer productId = cart.getProductId();
            //根据商品ID获取商品数据
            Product product = productMapper.selectByProductId(productId);
            if (product == null){
                return ServiceResponse.defeadetRs("商品不存在");
            }
            if ( product.getStatus() != 1) {
                return ServiceResponse.defeadetRs("商品以下架");
            }

            //校验库存
            if (cart.getQuantity()>product.getStock()){
                return ServiceResponse.defeadetRs("超出库存");
            }


            //根据购物车数量和商品单价计算一条购物车的总价
            BigDecimal mul = BigDecimalUtils.add(product.getPrice().doubleValue(), cart.getQuantity());
            //每一条购物车享家就是总价
            payment = BigDecimalUtils.add(payment.doubleValue(), mul.doubleValue());
            //放到集合备用
            productList.add(product);
        }

        //创建订单
        Orders orders = this.getOrder(uid, shippingid);
        int insert = ordersMapper.insert(orders);
        if (insert <= 0) {
            return ServiceResponse.defeadetRs("订单创建是吧");
        }
        //创建订单详情
        List<OrderItem> orderItemList = this.getOrderItem(uid, getOrderNo(), productList, cartList);
        int insertAll = orderItemMapper.insertAll(orderItemList);

        if (insertAll <= 0) {
            return ServiceResponse.defeadetRs(orders.getOrderNo() + "订单向平创建失败");
        }

        //插入成功减少库存
        for (OrderItem item : orderItemList) {
            for (Product product : productList) {
                if (item.getProductId() == product.getId()) {
                    Integer count = product.getStock() - item.getQuantity();
                    if (count < 0) {
                        return ServiceResponse.defeadetRs("库存不能为负数");
                    }
                    product.setStatus(count);
                    //更新数据到数据库中
                    int inPuoduct = productMapper.updateById(product);
                    if (inPuoduct < 0) {
                        return ServiceResponse.defeadetRs("更新失败");
                    }
                }
            }
        }

        //清空购物车
        int cartDelet = cartMapper.deleteAllByidAndUid(cartList, uid);
        if (cartDelet <= 0) {
            return ServiceResponse.defeadetRs("清空购物车失败");
        }

        //pinjie VOlei 返回数据

        List<OrderItemVo> itemVoList = new ArrayList<>();
        for (OrderItem orderItem : orderItemList) {
            OrderItemVo orderItemVo = PoToVoUtils.orderItemToOderItemVo(orderItem);
            itemVoList.add(orderItemVo);
        }
        //封装地址VO类
        ShippingVo shippingVo = PoToVoUtils.ShippingToShippingVo(shipping);
        OrderVo orderVo = new OrderVo();
        orderVo.setOrderItemVos(itemVoList);
        orderVo.setShippingVo(shippingVo);


        return null;
    }

    @Override
    public ServiceResponse getOrderItems(Integer uid, Long orderNo) {
        //根据用户ID和订单编号获取对应的订单详情信息
        if (orderNo != null){
           List<OrderItem> orderItemList= orderItemMapper.selectByOrderNoAndUid(orderNo,uid);
        }

        return null;
    }

    //创建订单对象
    private Orders getOrder(Integer uid, Integer shippingid) {
        Orders orders = new Orders();
        orders.setUserId(uid);
        orders.setOrderNo(this.getOrderNo());
        orders.setShippingId(shippingid);
        orders.setPaymentType(1);
        orders.setPostage(0);
        orders.setStatus(10);
        return null;
    }

    //根据订单详情及合获取orderitemvo集合
    private List<OrderItemVo> getOrderItemVoList(List<OrderItem>)


    //创建一个订单详情对象
    private List<OrderItem> getOrderItem(Integer uid, Long orderNo, List<Product> productList, List<Cart> cartList) {
        List<OrderItem> orderItemList = new ArrayList<OrderItem>();
        for (Cart cart : cartList) {
            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(cart.getQuantity());
            for (Product product : productList) {
                if (product.getId().equals(cart.getProductId())){
                    orderItem.setUserId(uid);
                    orderItem.setOrderNo(orderNo);
                    orderItem.setProductId(product.getId());
                    orderItem.setProductName(product.getName());
                    orderItem.setProductImage(product.getMainImage());
                    orderItem.setCurrentUnitPrice(product.getPrice());
                    BigDecimal mul = BigDecimalUtils.mul(product.getPrice().doubleValue(),cart.getQuantity().doubleValue());
                    orderItem.setTotalPrice(mul);
                    orderItemList.add(orderItem);

                }


            }
        }
        return orderItemList;
    }

    //生成订单编号
    private Long getOrderNo() {
        long l = System.currentTimeMillis();
        long orderNo = 1 + Math.round(Math.random() * 100);
        return orderNo;
    }
    //计算订单总价

//    private BigDecimal getBigPayment(List<Cart> cartList) {
//        BigDecimal payment = new BigDecimal("0");
//        for (Cart cart : cartList) {
//            //判断订单是否失效
//            Integer productId = cart.getProductId();
////            根据商品ID获取商品信息
//            Product product = productMapper.selectByProductId(productId);
//            if (product != null || product.getStatus() == 1) {
//                //根据购物车购物数量和商品单价计算一条购物车信息的总价
//                BigDecimal mul = BigDecimalUtils.mul(product.getPrice().doubleValue(), product.getPrice().doubleValue());
//                payment = BigDecimalUtils.add(payment.doubleValue(), mul.doubleValue());
//            } else {
//                payment = new BigDecimal(0);
//                return payment;
//            }
//
//
//        }
//    }
}
