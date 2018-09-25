package com.czxy.bos.controller;


import com.czxy.bos.constant.Constants;
import com.czxy.bos.domain.base.Area;
import com.czxy.bos.domain.take_delivery.Order;
import com.czxy.crm.domain.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/foreOrder")
public class ForeOrderController {

    @Resource
    private HttpSession session;

    @Resource
    private RestTemplate template;

    @PostMapping
    public ResponseEntity<String> add(Order order , String sendAreaInfo , String recAreaInfo){

        Area sendArea = new Area();
        String[] sendAreaData = sendAreaInfo.split("/");
        sendArea.setProvince(sendAreaData[0]);
        sendArea.setCity(sendAreaData[1]);
        sendArea.setDistrict(sendAreaData[2]);
        order.setSendArea(sendArea);

        Area recArea = new Area();
        String[] recAreaData = recAreaInfo.split("/");
        recArea.setProvince(recAreaData[0]);
        recArea.setCity(recAreaData[1]);
        recArea.setDistrict(recAreaData[2]);
        order.setRecArea(recArea);

        // 关联当前账户
        Customer customer = (Customer) session.getAttribute("customer");
        if ( customer == null ){
            return new ResponseEntity<>("用户没有登录" , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        order.setCustomer_id(customer.getId());

        // 访问bos_management
        String url = Constants.BOS_MANAGEMENT_HOST + "/bosOrder";
        System.out.println(url);
        return template.postForEntity( url , order , String.class);
    }
}
