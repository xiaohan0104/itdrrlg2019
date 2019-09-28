package com.itdr.pojo.vo;

import java.math.BigDecimal;
import java.util.List;

public class OrderItemVoTwo {

    private List<OrderItemVo> orderItemVoList;
    private String imaageHost;
    private BigDecimal productTotalprice;

    public List<OrderItemVo> getOrderItemVoList() {
        return orderItemVoList;
    }

    public void setOrderItemVoList(List<OrderItemVo> orderItemVoList) {
        this.orderItemVoList = orderItemVoList;
    }

    public String getImaageHost() {
        return imaageHost;
    }

    public void setImaageHost(String imaageHost) {
        this.imaageHost = imaageHost;
    }

    public BigDecimal getProductTotalprice() {
        return productTotalprice;
    }

    public void setProductTotalprice(BigDecimal productTotalprice) {
        this.productTotalprice = productTotalprice;
    }
}
