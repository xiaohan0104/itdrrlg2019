package com.itdr.services.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.itdr.common.Const;
import com.itdr.common.ServiceResponse;
import com.itdr.mappers.*;
import com.itdr.pojo.OrderItem;
import com.itdr.pojo.Orders;
import com.itdr.pojo.PayInFo;
import com.itdr.pojo.pay.Configs;
import com.itdr.pojo.pay.ZxingUtils;
import com.itdr.services.AliPayService;
import com.itdr.util.DateUtils;
import com.itdr.util.JsonUtils;
import com.itdr.util.PoToVoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AliPayServiceImpl implements AliPayService {
    ServiceResponse sr = null;
    @Autowired
    private OrdersMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ShippingMapper shippingMapper;
    @Autowired
    private PayInFoMapper payInFoMapper;
    @Override
    public ServiceResponse alipay(Long orderno, Integer id) {

        if (orderno == null || orderno<=0){
            return ServiceResponse.defeadetRs(Const.FEIFACANSHU);
        }
        //查询订单是否存在
        Orders orders = orderMapper.selectByOrderNo(orderno);
        if (orders == null){
            return ServiceResponse.defeadetRs(Const.NOTHING);
        }
//查询订单是否匹配
//        int num = orderMapper.selectByOrderNoAndUid(orderno,id);
//        if (num <= 0){
//            return ServiceResponse.defeadetRs(Const.NOTHING);
//        }

        //根据订单号查询对象商品详情
        List<OrderItem> orderItems = orderItemMapper.selectByOrderNo(orders.getOrderNo());
        //调用支付宝接口获取支付二维码
        try {
            AlipayTradePrecreateResponse alipayTradePrecreateResponse = test_trade_precreate(orders, orderItems);
            if (alipayTradePrecreateResponse.isSuccess()){
                //将二维码信息串成图片，并保存，（需要修改为运行机器上的路径）
                String filePath = String.format(Configs.getSavecode_test()+"qr-%s.png",alipayTradePrecreateResponse.getOutTradeNo());
                ZxingUtils.getQRCodeImge(alipayTradePrecreateResponse.getQrCode(),256,filePath);
            //预下单成功返回信息
                Map map = new HashMap();
                map.put("orderNo",orders.getOrderNo());
                map.put("qrCode",filePath);
                return ServiceResponse.successRs(map);
            }else {
                return ServiceResponse.defeadetRs("下单失败");
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return ServiceResponse.defeadetRs("下单失败");

        }
       //后期图片都会放到图片服务器
    }
//毁回调参数
    @Override
    public ServiceResponse apilpayCallback(Map<String, String> params) {
        //获取获取orderNo订单少
        Long orderNo = Long.parseLong(params.get("out_trad_no"));
        //获取流水号
        String tarde_no = params.get("tarde_no");
        //获取支付时间
        String payment_time = params.get("payment_time");
        //获取支付状态
        String trade_status = params.get("trade_status");
        //获取订单金额
        BigDecimal total_amount = new BigDecimal(params.get("total_amount"));

        Orders orders = orderMapper.selectByOrderNo(orderNo);
        if (orders == null){
            //不是要付款的订单
            sr = ServiceResponse.defeadetRs("不是要付款的订单");
            return sr;
        }

        if (orders.getStatus() != 10){
            //防止重复回调
            sr = ServiceResponse.defeadetRs("不是未支付状态");
            return sr;
        }
        if (trade_status.equals("TRADE_SUCCESS")){
            //检验状态吗 支付成功
            //更新数据库订单状态，更改支付时间更新时间删除本地二维码
            orders.setStatus(20);
            orders.setPaymentTime(DateUtils.strToDate(payment_time));
            orderMapper.updateByPrimaryKey(orders);

            //支付成功 删除二维码
            String str = String.format(Configs.getSavecode_test()+"qr-%s.pag",
                    orders.getOrderNo());
            File file = new File(str);
            boolean b = file.delete();
        }
        //保存支付宝支付信息
        PayInFo payInFo = new PayInFo();
        payInFo.setOrderNo(orderNo);
        payInFo.setPayPlatform(1);
        payInFo.setPlatformStatus(trade_status);
        payInFo.setPlatformNumber(tarde_no);
        payInFo.setUserId(orders.getUserId());

        int result = payInFoMapper.insert(payInFo);
        if (result<= 0){

            sr = ServiceResponse.defeadetRs("支付失败");
            return sr;
        }
        //支付信息保存返回结果SUCCESS,让支付宝不在回调
        sr = ServiceResponse.successRs("SUCCESS");
        return sr;
    }


    //生成二维码
    private AlipayTradePrecreateResponse test_trade_precreate(Orders order, List<OrderItem> orderItems) throws AlipayApiException {
        //读取配置文件信息
        Configs.init("zfbinfo.properties");

        //实例化支付宝客户端
        AlipayClient alipayClient = new DefaultAlipayClient(Configs.getOpenApiDomain(),
                Configs.getAppid(), Configs.getPrivateKey(), "json", "utf-8",
                Configs.getAlipayPublicKey(), Configs.getSignType());

        //创建API对应的request类
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();

        //获取一个BizContent对象,并转换成json格式
        String str = JsonUtils.obj2String(PoToVoUtils.getBizContent(order, orderItems));
        request.setBizContent(str);
        //设置支付宝回调路径
        request.setNotifyUrl(Configs.getNotifyUrl_test());
        //获取响应,这里要处理一下异常在·
        AlipayTradePrecreateResponse response = alipayClient.execute(request);

        //返回响应的结果
        return response;
    }
}
