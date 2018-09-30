package com.czxy.bos.service.system;

import com.czxy.bos.dao.system.UserMapper;
import com.czxy.bos.domain.system.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

@Service
@Transactional
public class UserService {

    @Resource
    private UserMapper userMapper;

    public User findUserByUsername(String userName){

        Example example = new Example(User.class);

        example.createCriteria().andEqualTo("username",userName);
        return userMapper.selectOneByExample(example);
    }
}
