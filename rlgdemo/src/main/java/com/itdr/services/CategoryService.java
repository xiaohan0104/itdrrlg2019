package com.itdr.services;

import com.itdr.common.ServiceResponse;

public interface CategoryService {
    //根据分类ID查询所有子类
    ServiceResponse getDeepCategory(Integer categoryId);
}
