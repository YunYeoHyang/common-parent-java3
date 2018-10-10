package com.czxy.bos.dao.system;

import com.czxy.bos.domain.system.Menu;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface MenuMapper extends Mapper<Menu> {

    @Select("SELECT m.* FROM t_menu m " +
            "INNER JOIN t_role_menu rm ON m.id = rm.menu_id " +
            "INNER JOIN t_role r ON rm.role_id = r.id " +
            "INNER JOIN t_user_role ur ON r.id = ur.role_id " +
            "INNER JOIN t_user u ON ur.user_id = u.id " +
            "WHERE u.id= #{id} ORDER BY m.priority ")
    public List<Menu> findByUser(Integer id);
}
