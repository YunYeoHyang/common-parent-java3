package com.czxy.bos.dao.system;

import com.czxy.bos.domain.system.User;
import tk.mybatis.mapper.common.Mapper;

@org.apache.ibatis.annotations.Mapper
public interface UserMapper extends Mapper<User> {

    public User findUserByUserName(String userName);
}
