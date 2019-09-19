package com.itdr.controllers.portal;

import com.itdr.common.ServiceResponse;
import com.itdr.pojo.Product;
import com.itdr.pojo.Users;
import com.itdr.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/product/")
@ResponseBody
public class ProductController {
    @Autowired
    ProductService productService;

//获取商品分类信息
    @RequestMapping("topcategory.do")
    public ServiceResponse topCategory(Integer pid){
        ServiceResponse productOneById = productService.topCategory(pid);
        return productOneById;
    }


    //获取商品详情
    @RequestMapping("detail")
    public ServiceResponse<Users> detail(Integer productId,
                                         @RequestParam(value = "is_new",required = false,defaultValue = "0")Integer is_new,
                                         @RequestParam(value = "is_hot",required = false,defaultValue = "0")Integer is_hot,
                                         @RequestParam(value = "is_banner",required = false,defaultValue = "0")Integer is_banner){
        return productService.detail(productId,is_banner,is_hot,is_new);
    }


    //商品搜索加动态排序
    @PostMapping("list.do")
    public ServiceResponse<Users> listProduct(Integer productId,String keyWord,
                                              @RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum,
                                              @RequestParam(value = "pageSize" ,required = false,defaultValue = "10") Integer pageSize,
                                              @RequestParam(value = "orderBy" ,required = false,defaultValue = "")String  orderBy){
        return productService.listProduct(productId,keyWord,pageNum,pageSize,orderBy);

    }
}













