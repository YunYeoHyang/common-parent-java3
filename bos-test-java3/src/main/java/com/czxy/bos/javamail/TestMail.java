package com.czxy.bos.javamail;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by liangtong on 2018/9/13.
 */
public class TestMail {

    //所有的内容都来找同一个包  javax.mail
    public static void main(String[] args) throws Exception {

        //0.1 准备参数（固定内容）
        Properties props = new Properties();
        props.setProperty("mail.smtp.host","smtp.163.com");     //确定发送邮件服务器
        props.setProperty("mail.smtp.auth","true");             //开启认证
        //0.2 认证信息(账号和密码)
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("itcast_lt@163.com","1qaz2wsx");
            }
        };

        //1 创建连接（会话）-- Session
        Session session = Session.getDefaultInstance(props,authenticator);

        //2 编写邮件（消息） --Message
        Message message = new MimeMessage( session );
        //2.1 发送人
        message.setFrom(new InternetAddress("itcast_lt@163.com"));
        //2.2 收件人 , TO 收件 ， CC 抄送 ，BCC暗送
        message.setRecipient( Message.RecipientType.TO , new InternetAddress("itcast_lt@126.com"));
        //2.3 主题
        message.setSubject("第一个java发送邮件");
        //2.4 正文
        message.setContent("我给你发送了一份邮件，晚上看着办，有想法<a href='http://www.czxy.com'>点我</a>","text/html;charset=UTF-8");

        //3 发送 Transport
        Transport.send(message);
        System.out.println("发送成功");

    }
}
