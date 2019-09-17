package com.itdr.controllers.backend;

import com.itdr.common.ServiceResponse;
import com.itdr.services.CategoryService;
import com.itdr.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/manage/category/")
public class CategoryManageController {
    @Autowired
    CategoryService categoryService;
    //根据分类ID查询所有子类
    @RequestMapping("get_deep_category.do")
    public ServiceResponse getDeepCategory(Integer categoryId){
        ServiceResponse sr = categoryService.getDeepCategory(categoryId);
        return sr;
    }
}
