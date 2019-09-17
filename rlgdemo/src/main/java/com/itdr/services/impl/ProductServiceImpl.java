package com.itdr.services.impl;

import com.itdr.common.ServiceResponse;
import com.itdr.mappers.ProductMapper;
import com.itdr.pojo.Product;
import com.itdr.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService  {
    @Autowired
    ProductMapper productMapper;


    @Override
    public ServiceResponse getProductOneById(Integer pid) {
        Product productOneById = productMapper.getProductOneById(pid);
        return ServiceResponse.successRs(productOneById);
    }
}
