package com.czxy.bos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by liangtong on 2018/8/30.
 */
@SpringBootApplication
@EnableScheduling           //开启定时器
public class BosApplication {
    public static void main(String[] args) {
        SpringApplication.run(BosApplication.class,args);
    }
}
