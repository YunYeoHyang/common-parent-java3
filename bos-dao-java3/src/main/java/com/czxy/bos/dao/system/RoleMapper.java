package com.czxy.bos.dao.system;

import com.czxy.bos.domain.system.Role;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface RoleMapper extends Mapper<Role> {

    @Select("SELECT * FROM t_role r WHERE r.id IN (SELECT ur.role_id FROM t_user_role ur WHERE ur.user_id = #{userId})")
    List<Role> findByUser(int userId);
}
