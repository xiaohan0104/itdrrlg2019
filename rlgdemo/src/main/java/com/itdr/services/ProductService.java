package com.itdr.services;

import com.itdr.common.ServiceResponse;
import com.itdr.pojo.Product;
import com.itdr.pojo.Users;

public interface ProductService {
    //获取商品分类信息
    ServiceResponse topCategory(Integer pid);
    //获取商品详情
    ServiceResponse<Users> detail(Integer productId, Integer is_banner, Integer is_hot, Integer is_new);
    //商品搜索加动态排序
    ServiceResponse<Users> listProduct(Integer productId, String keyWord, Integer pageNum, Integer pageSize, String orderBy);
}
