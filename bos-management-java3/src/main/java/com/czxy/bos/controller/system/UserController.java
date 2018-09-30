package com.czxy.bos.controller.system;

import com.czxy.bos.domain.system.User;
import com.czxy.bos.service.system.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 员工登录
     * @param user
     * @param session
     * @return
     */
    @GetMapping("/login")
    public ResponseEntity<String> login(User user, HttpSession session){
        // 用户名和密码 都保存在model中
        try {
            //1 通过shiro进行用户登录
            SecurityUtils.getSubject().login(new UsernamePasswordToken(user.getUsername(), user.getPassword()));
            // 登录成功
            // 将用户信息 保存到 Session
            User loginUser = (User) SecurityUtils.getSubject().getPrincipal();
            session.setAttribute("user", loginUser);

            return new ResponseEntity<String>("登录成功" , HttpStatus.OK);
        } catch (IncorrectCredentialsException e) {
            // 登录失败
            return new ResponseEntity<String>("密码错误" , HttpStatus.INTERNAL_SERVER_ERROR);
        } catch(Exception e){
            return new ResponseEntity<String>("登录失败" , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
