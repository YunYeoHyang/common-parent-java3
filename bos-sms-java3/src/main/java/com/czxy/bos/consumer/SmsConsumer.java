package com.czxy.bos.consumer;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.czxy.bos.util.SmsUtil;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;

/**
 * 将自定义类配置spring容器：
 *      web层：@Controller、@RestController
 *      service层：@Service
 *      dao层：@Mapper、@Repository（未用）
 *      其他任意：@Component
 * Created by liangtong on 2018/9/17.
 */
@Component
public class SmsConsumer {
    /**
     * 此方法用于接收消息
     */
    @JmsListener(destination = "czxy.queue")
    public void receiveQueue(MapMessage mapMessage){
        try {
            String telephone = mapMessage.getString("telephone");
            String randomStr = mapMessage.getString("randomStr");
            System.out.println("消费者：" + telephone + " , " + randomStr);

            //2 发送短信
//            SendSmsResponse sendSmsResponse = SmsUtil.sendSms(telephone,"帅锅",randomStr,"上海花园","119");
//            System.out.println(sendSmsResponse.getMessage());


        } catch (Exception e) {
        }

    }


}
