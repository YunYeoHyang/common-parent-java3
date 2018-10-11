package com.czxy.bos.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;

/**
 * Created by liangtong on 2018/9/17.
 */
@Configuration
public class ActiveMQConfig {


    @Bean
    public Queue queue() {
        return new ActiveMQQueue("czxy.queue");
    }

}
