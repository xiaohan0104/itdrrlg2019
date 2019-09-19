package com.itdr.mappers;

import com.itdr.pojo.Product;
import com.itdr.pojo.ProductWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductWithBLOBs record);

    int insertSelective(ProductWithBLOBs record);

    ProductWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ProductWithBLOBs record);

    int updateByPrimaryKey(Product record);

    //获取商品详情
    Product selectById(@Param("productId") Integer productId,
                       @Param("is_banner")Integer is_banner,
                       @Param("is_hot")Integer is_hot,
                       @Param("is_new")Integer is_new);

    //商品搜索加动态排序
    List<Product> selectByIdOrName(@Param("productId")Integer productId,
                          @Param("keyWord")String keyWord,
                          @Param("col")String col,
                          @Param("order")String order);
}