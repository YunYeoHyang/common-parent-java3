package com.czxy.bos.controller;

import com.czxy.crm.domain.Customer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Controller
@RequestMapping("/customer")
public class CustomerActiveController {

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private StringRedisTemplate redisTemplate;


    /**
     * 邮件激活后，到登录页面显示激活提示信息
     * @param telephone
     * @param activecode
     * @return
     */
    @GetMapping("/activeMail")
    public String activeMail(String telephone , @RequestParam( "activeCode" ) String activecode, Model model){
        System.out.println("激活");
        //如果判断用户提供激活码正确的？
        //1 从redis 缓存服务器中，通过“手机号”获得对应激活码
        String redisActiveCode = redisTemplate.opsForValue().get(telephone);

        System.out.println(activecode.substring(0,32));
        System.out.println(redisActiveCode);
        //2 判断激活码是否相同
        if(redisActiveCode == null || ! redisActiveCode.equals(activecode.substring(0,32))){
            model.addAttribute("msg" , "激活码无效");
            return "login";
        }

        //删除redis存放的激活码，已经使用了
        redisTemplate.delete(telephone);

        //3 使用telephone通过crm系统查询客户，如果状态不是0，提示该账户不能激活。
        String url = "http://localhost:8090/crmCustomer/findByTelephone?telephone=" + telephone;
        ResponseEntity<Customer> entity = restTemplate.getForEntity(url,Customer.class);
        Customer customer = entity.getBody();
        if(customer.getType() != 0){
            model.addAttribute("msg" , "该账号不能激活");
            return "login";
        }

        //4 通过crm系统，激活账号（修改状态为==1，=），提示用户账号激活成功。
        url = "http://localhost:8090/crmCustomer/updateType?telephone=" + telephone;
        ResponseEntity<String> entity2 = restTemplate.getForEntity(url,String.class);

        model.addAttribute("msg" , entity2.getBody() );
        return "login";
    }
}
