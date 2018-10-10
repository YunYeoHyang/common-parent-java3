package com.czxy.bos.service.system;

import com.czxy.bos.dao.system.UserMapper;
import com.czxy.bos.dao.system.UserRoleMapper;
import com.czxy.bos.domain.system.User;
import com.czxy.bos.domain.system.UserRole;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    public User findUserByUsername(String userName){

        Example example = new Example(User.class);

        example.createCriteria().andEqualTo("username",userName);
        return userMapper.selectOneByExample(example);
    }

    /**查询所有的用户*/
    public PageInfo<User> findAllUserList(Integer page , Integer rows) {
        PageHelper.startPage( page , rows );
        List<User> list = userMapper.selectAll();
        return new PageInfo<>( list );
    }

    /**保存用户*/
    public void saveUser(User user, String[] roleIds) {
        //1 保存用户
        userMapper.insert(user);
        //2 保存用户和角色的关系
        for (String id : roleIds) {
            UserRole ur = new UserRole();
            ur.setUserId(user.getId());
            ur.setRoleId(Integer.parseInt(id));
            userRoleMapper.insert(ur);
        }
    }
}
