package com.czxy.bos.controller;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.czxy.bos.constant.Constants;
import com.czxy.bos.util.MailUtil;
import com.czxy.bos.util.SmsUtil;
import com.czxy.crm.domain.Customer;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Queue;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Resource
    private HttpSession session;

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private StringRedisTemplate redisTemplate;

    @Resource
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Resource
    private Queue queue;

    @Resource
    private HttpServletRequest request;


    @GetMapping("/sendSms")
    public ResponseEntity<Void> sendSms(String telephone) {
        //1 生成4位验证码
        String randomStr = RandomStringUtils.randomNumeric(4);
        System.out.println("本次验证码是：" + randomStr);

        //TODO 暂时将验证码存放到session
        session.setAttribute("sessionCheckcode", randomStr);

        //2 发送短信 --此段代码移到 bos-sms系统的SmsConsumer类中
        /*try {
            //SendSmsResponse sendSmsResponse = SmsUtil.sendSms(telephone,"帅锅",randomStr,"上海花园","119");
            //System.out.println(sendSmsResponse.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        //将发送短信整个业务需要数据，添加到消息队列(activemq)中 (使发送短信业务与我们的项目解耦)
        try {
            MapMessage mapMessage = new ActiveMQMapMessage();
            mapMessage.setString("telephone", telephone);
            mapMessage.setString("randomStr", randomStr);
            jmsMessagingTemplate.convertAndSend(queue, mapMessage);
        } catch (JMSException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping("/regist")
    public ResponseEntity<String> regist(String checkcode, Customer customer) {

        //1 判断验证码是否正确的，从session获得验证码，获得用户输入验证码匹配
        String sessionCheckcode = (String) session.getAttribute("sessionCheckcode");
        if (!sessionCheckcode.equals(checkcode)) {
            return new ResponseEntity<String>("验证码错误", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        System.out.println("fore前端发送成功");
        //2 通过crm系统进行用户注册，第二个为请求参数，发送过去的数据为json
        //String url = "http://localhost:8090/crmCustomer/saveCustomer";
        String url = Constants.CRM_MANAGEMENT_HOST + "/crmCustomer/saveCustomer";
        ResponseEntity<String> entity = restTemplate.postForEntity(url, customer, String.class);

        //crm系统返回201表示注册成功的
        if (entity.getStatusCodeValue() == 201) {
            try {
                //激活码
                String activeCode = RandomStringUtils.randomNumeric(32);
                //将激活码存放到redis缓存服务器中（手机号=激活码）
                redisTemplate.opsForValue().set(customer.getTelephone(), activeCode, 24, TimeUnit.HOURS);

                System.out.println(activeCode);

                //激活链接
                String activeUrl = "http://localhost:8088/customer/activeMail?telephone=" + customer.getTelephone() + "&activeCode=" + activeCode;

                //发送激活邮件
                String text = "手机用户[" + customer.getTelephone() + "]您好：<br/>你正在“速运快递”注册用户，请<a href='" + activeUrl + "'>点击</a>进行激活，激活码有效时间24小时。<br/>若无法连接，可直接点击" + activeUrl + ". <br/>若不是本人，请删除邮件。";
                MailUtil.sendMail(customer.getEmail(), "【速运快递】账号激活邮件", text);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return entity;
    }

    @GetMapping("/validatecode")
    public ResponseEntity<Void> validatecode(HttpServletResponse response) throws IOException {

        int width = 80;
        int height = 30;

        //1 创建图片
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        //2 获得画板
        Graphics g = image.getGraphics();

        //3 绘制内容
        //3.1 绘制矩形：(注意先选择颜色)
        g.setColor(Color.WHITE);
        g.fillRect(1, 1, width - 2, height - 2);

        Random random = new Random();
        //3.3 绘制干扰线
        for (int i = 0; i < 10; i++) {
            //设置随机颜色 (红绿蓝3色，每一个颜色取值0-255)
            g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
            //绘制线
            g.drawLine(random.nextInt(width), random.nextInt(height), random.nextInt(width), random.nextInt(height));
        }

        //3.2 绘制随机数字
        String data = "abcdefghigjlmnopqrxtvuwxyz0123456789ABCDEFGHIGJLMNOPQRXTVUWXYZ";
        // 用于存放随机生成4个字符
        StringBuilder sb = new StringBuilder();
        //设置颜色
        g.setFont(new Font("宋体", Font.BOLD, 20));
        for (int i = 0; i < 4; i++) {
            //设置随机颜色 (红绿蓝3色，每一个颜色取值0-255)
            g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
            int index = random.nextInt(data.length());
            //截取一个字符
            String str = data.substring(index, index + 1);
            sb.append(str);
            //将字符写入图片中 (整个宽度切割6部分，两边留白，中间4个写字符)
            g.drawString(str, width / 6 * (i + 1), 20);
        }

        //将随机4个字符，拼凑成字符串，添加到session中
        request.getSession().setAttribute("validateCode", sb.toString());

        //4 将图片响应给浏览器
        ImageIO.write(image, "jpeg", response.getOutputStream());


        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(String telephone, String password, String checkcode) {
        //1 验证码是否正确
        // 1.1 获得session验证码
        String sessionValidateCode = (String) request.getSession().getAttribute("validateCode");
        // 1.2 判断
        if (sessionValidateCode == null) {
            return new ResponseEntity<String>("验证码失效", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (!sessionValidateCode.equalsIgnoreCase(checkcode)) {
            return new ResponseEntity<String>("验证码错误", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // 1.3 将session验证码移除
        request.getSession().removeAttribute("validateCode");

        //2 通过crm系统查询用户
        String url = "http://localhost:8090/crmCustomer/findCustomerTelephoneAndPassword?telephone=" + telephone + "&password=" + password;
        ResponseEntity<Customer> entity = restTemplate.getForEntity(url, Customer.class);
        Customer customer = entity.getBody();
        //3 判断
        //3.1 为null，没有查询结果
        if (customer == null) {
            return new ResponseEntity<String>("用户名或密码不匹配", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //3.2 有结果，状态不是1（不是激活状态）
        if (customer.getType() != 1) {
            return new ResponseEntity<String>("当前用户不能登录", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //3.3 正常
        // 3.1 登录成功后的标志，就是将用户添加到了session
        request.getSession().setAttribute("customer", customer);
        // 3.2 提示
        return new ResponseEntity<String>("登录成功", HttpStatus.OK);
    }


}
