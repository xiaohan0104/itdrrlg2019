package com.itdr.controllers.portal;

import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayDaoweiServiceModifyModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.itdr.common.Const;
import com.itdr.common.ServiceResponse;
import com.itdr.pojo.Users;
import com.itdr.pojo.pay.Configs;
import com.itdr.services.AliPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
@RequestMapping("/pay/")
@ResponseBody
public class AliPauContontroller {
    @Autowired
    AliPayService aliPayService;

    //   支付订单
    @RequestMapping("alipay.do")
    private ServiceResponse alipay(Long orderno, HttpSession session) {
        Users users = (Users) session.getAttribute(Const.LOGINUSER);
        if (users == null) {
            return ServiceResponse.defeadetRs(Const.UsersEnum.ON_LOGIN.getCode(), Const.UsersEnum.ON_LOGIN.getDesc());
        }
        return aliPayService.alipay(orderno, users.getId());
    }
    //查纯支付状态

//    @RequestMapping("query_order_pay_status.do")
//    private ServiceResponse queryOrderPayStatus(Long orderNo, HttpSession session){
//
//    }



    //支付宝毁掉

    @RequestMapping("alipay_callback.do")
    private ServiceResponse alipayCallback(HttpServletRequest request, HttpServletResponse response) {
        ServiceResponse sr = null;
//获取支付返回的参数，返回一个mao集合
        Map<String, String[]> map = request.getParameterMap();
        //获取该集合的迭代器
        Iterator<String> iterator = map.keySet().iterator();
        //创建一个新的map集合用于崔楚支付宝的数据，去除不必要的数据
        Map<String, String> params = new HashMap<>();
        //遍历集合，键得集合中数据
        while (iterator.hasNext()) {
            String next = iterator.next();
            String[] strings = map.get(next);
            String values = "";
            for (int i = 0; i < strings.length; i++) {
                //如果只有一个元素，就保存一个元素
                //如果有多个元素就用逗号隔开
                values = (i == strings.length - 1) ? values + strings[1] : values + strings[i] + ",";
            }
            params.put(next, values);
        }
        //支付宝验签，是不是支付宝发出的请求，

        try {
            params.remove("sign_type");
            boolean result = AlipaySignature.rsaCheckV2(params, Configs.getAlipayPublicKey(), "UTF-8", Configs.getSignType());
            if (!result) {
                ///验签失败返回错误信息
                sr = ServiceResponse.defeadetRs("失败");

                return sr;
            }
        } catch (AlipayApiException e) {
            sr = ServiceResponse.defeadetRs("失败");
            return sr;
        }
        //通过业务层执行
       sr = aliPayService.apilpayCallback(params);

            try {
                if (sr.issuccerd()){
                response.getWriter().print("SUCCESS");
                }else {
                    response.getWriter().print("FAILED");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

return sr;

    }


}
