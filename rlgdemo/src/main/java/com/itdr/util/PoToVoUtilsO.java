package com.itdr.util;

import com.itdr.common.Const;
import com.itdr.pojo.Cart;
import com.itdr.pojo.Product;
import com.itdr.pojo.vo.CartProductVo;
import com.itdr.pojo.vo.ProductVO;

import java.math.BigDecimal;

public class PoToVoUtilsO {

    public static ProductVO productVOTovo(Product p) {

        ProductVO productVO = new ProductVO();
        productVO.setImageHost(PropertiesUtils.getValue("imageHost"));

        productVO.setCategoryId(p.getCategoryId());
        productVO.setCreateTime(p.getCreateTime());
        productVO.setDetail(p.getDetail());
        productVO.setId(p.getId());
        productVO.setIsBanner(p.getIsBanner());
        productVO.setIsHot(p.getIsHot());
        productVO.setIsNew(p.getIsNew());
        productVO.setMainImage(p.getMainImage());
        productVO.setName(p.getName());
        productVO.setPrice(p.getPrice());
        productVO.setStatus(p.getStatus());
        productVO.setStock(p.getStock());
        productVO.setSubImages(p.getSubImages());
        productVO.setSubtitle(p.getSubtitle());
        productVO.setUpdateTime(p.getUpdateTime());

        return productVO;
    }


    public static CartProductVo addCartProductVo(Cart cart, Product product) {
//封装对象不能为空

        CartProductVo cartProductVo = new CartProductVo();

        cartProductVo.setId(cart.getId());
        cartProductVo.setUserId(cart.getUserId());
        cartProductVo.setProductId(cart.getProductId());
        cartProductVo.setProductChecked(cart.getChecked());

        if (product != null) {
            cartProductVo.setName(product.getName());
            cartProductVo.setSubtitle(product.getSubtitle());
            cartProductVo.setMainImage(product.getMainImage());
            cartProductVo.setPrice(product.getPrice());
            cartProductVo.setStatus(product.getStatus());
            cartProductVo.setStatus(product.getStatus());

            //总价
            BigDecimal productTotalPrice = BigDecimalUtils.mul(cart.getQuantity(), product.getPrice().doubleValue());
            cartProductVo.setProductTotalPrice(productTotalPrice);

            int quantify = 0;
            //判断库存信息
            if (cart.getQuantity() <= product.getStock()) {
                quantify = cart.getQuantity();
                cartProductVo.setLimitQuantity(Const.LIMTQUANTYSUCCESS);
            } else {
                quantify = product.getStock();
                cartProductVo.setLimitQuantity(Const.LIMTQUANTYFATLED);
            }
            cartProductVo.setQuantity(quantify);
        }
        return cartProductVo;
    }
}
