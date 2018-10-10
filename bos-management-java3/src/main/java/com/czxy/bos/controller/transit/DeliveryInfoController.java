package com.czxy.bos.controller.transit;

import com.czxy.bos.service.transit.DeliveryInfoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("delivery")
public class DeliveryInfoController {

    @Resource
    private DeliveryInfoService deliveryInfoService;
}
