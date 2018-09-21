package com.czxy.bos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@RequestMapping("/forePromotion")
public class ForePromotionController {

    @Resource
    private RestTemplate restTemplate;

    /**
     * 查询所有
     * @return
     */
    @GetMapping
    public ResponseEntity<String> queryPromotionByPage(){
        //1 访问bos后端系统查询
        String url = "http://localhost:8080/promotion/findAll";
        return restTemplate.getForEntity(url ,String.class);
    }


}
