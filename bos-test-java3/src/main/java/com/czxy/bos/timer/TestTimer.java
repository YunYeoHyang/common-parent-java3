package com.czxy.bos.timer;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by liangtong on 2018/9/19.
 */
@Component
public class TestTimer {
    //每3秒方法调用一次
    @Scheduled(cron = "0/3 * * * * ?")
    public void demo() {
        System.out.println(System.currentTimeMillis());
    }

}
