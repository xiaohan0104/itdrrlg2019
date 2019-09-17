package com.itdr.controllers.portal;

import com.itdr.common.ServiceResponse;
import com.itdr.pojo.Product;
import com.itdr.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/product/")
@ResponseBody
public class ProductController {
    @Autowired
    ProductService productService;


    @RequestMapping("topcategory.do")
    public ServiceResponse topcategory(Integer pid){
        ServiceResponse productOneById = productService.getProductOneById(pid);
        return productOneById;
    }


}
