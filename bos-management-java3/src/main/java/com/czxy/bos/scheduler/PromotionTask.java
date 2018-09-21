package com.czxy.bos.scheduler;

import com.czxy.bos.service.take_delivery.PromotionService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by liangtong on 2018/9/19.
 */
@Component
public class PromotionTask {

    @Resource
    private PromotionService promotionService;

    @Scheduled(cron="0 0/1 * * * ?")
    public void process(){
        promotionService.updateWithEndDate();
    }


}
