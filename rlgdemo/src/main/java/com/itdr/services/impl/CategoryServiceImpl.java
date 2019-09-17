package com.itdr.services.impl;

import com.itdr.common.ServiceResponse;
import com.itdr.mappers.CategoryMapper;
import com.itdr.pojo.Category;
import com.itdr.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    //根据分类ID查询所有子类
    @Override
    public ServiceResponse getDeepCategory(Integer categoryId) {
        List<Integer> li = new ArrayList<>();
        li.add(categoryId);
        getAll(categoryId, li);
        ServiceResponse rs = ServiceResponse.successRs(li);
        return rs;
    }

    private void getAll(Integer pid, List<Integer> list) {
        List<Category> li = categoryMapper.selectByParentId(pid);

        if (li != null && li.size() != 0) {

            for (Category category : li) {
                list.add(category.getId());
                getAll(category.getId(), list);
            }
        }
    }


}

