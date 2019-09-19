package com.itdr.util;

import com.itdr.pojo.Product;
import com.itdr.pojo.vo.ProductVO;

public class PoToVoUtils {

    public static ProductVO productVOTovo(Product p){

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

}
