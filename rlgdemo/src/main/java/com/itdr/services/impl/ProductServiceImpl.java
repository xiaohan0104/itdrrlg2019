package com.itdr.services.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itdr.common.ServiceResponse;
import com.itdr.mappers.CategoryMapper;
import com.itdr.mappers.ProductMapper;
import com.itdr.pojo.Category;
import com.itdr.pojo.Product;
import com.itdr.pojo.Users;
import com.itdr.pojo.vo.ProductVO;
import com.itdr.services.ProductService;
import com.itdr.util.PoToVoUtilsO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMapper productMapper;
    @Autowired
    CategoryMapper categoryMapper;

    //获取商品分类信息
    @Override
    public ServiceResponse topCategory(Integer pid) {
        if (pid == null || pid < 0) {
            return ServiceResponse.defeadetRs("非法的参数");
        }

        //根据id查询子分类
        List<Category> categoryList = categoryMapper.selectByParentId(pid);
        if (categoryList == null) {
            return ServiceResponse.defeadetRs("查询的id不存在");
        }
        if (categoryList.size() == 0) {
            return ServiceResponse.defeadetRs("没有子分类");
        }
        return ServiceResponse.successRs(categoryList);
    }

    //获取商品详情
    @Override
    public ServiceResponse<Users> detail(Integer productId, Integer is_banner, Integer is_hot, Integer is_new) {
        if (productId == null || productId < 0) {
            return ServiceResponse.defeadetRs("非法的参数");
        }

        Product product = productMapper.selectById(productId, is_banner, is_hot, is_new);
        if (product == null) {
            return ServiceResponse.defeadetRs("商品不存在");
        }

        ProductVO productVO = PoToVoUtilsO.productVOTovo(product);
        return ServiceResponse.successRs(productVO);
    }

    //商品搜索加动态排序
    @Override
    public ServiceResponse<Users> listProduct(Integer productId, String keyWord, Integer pageNum, Integer pageSize, String orderBy) {

        if ((productId == null || productId < 0) && (keyWord == null || keyWord.equals(""))) {
            return ServiceResponse.defeadetRs("非法参数");
        }
        String[] spilt=new String[2];
        if (!orderBy.equals("")){
            spilt = orderBy.split("_");

        }

        PageHelper.startPage(pageNum,pageSize);
        List<Product> products =productMapper.selectByIdOrName(productId,keyWord,spilt[0],spilt[1]);
        PageInfo pf = new PageInfo(products);


        return ServiceResponse.successRs(pf);
    }
}
