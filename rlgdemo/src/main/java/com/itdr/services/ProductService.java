package com.itdr.services;

import com.itdr.common.ServiceResponse;
import com.itdr.pojo.Product;

public interface ProductService {
    ServiceResponse getProductOneById(Integer pid);
}
