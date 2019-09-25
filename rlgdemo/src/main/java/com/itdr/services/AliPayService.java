package com.itdr.services;

import com.itdr.common.ServiceResponse;

import java.util.Map;

public interface AliPayService {
    ServiceResponse alipay(Long orderno, Integer id);

    ServiceResponse apilpayCallback(Map<String, String> params);
}
