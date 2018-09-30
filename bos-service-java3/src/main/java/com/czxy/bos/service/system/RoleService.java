package com.czxy.bos.service.system;

import com.czxy.bos.dao.system.RoleMapper;
import com.czxy.bos.domain.system.Role;
import com.czxy.bos.domain.system.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class RoleService {

    @Resource
    private RoleMapper roleMapper;

    /**
     * 查找指定用户授权的所有角色 （如果是admin用户，将查询所有角色）
     * @param user
     * @return
     */
    public List<Role> findByUser(User user ){

        if ( user.getUsername().equals("admin") ){
            return roleMapper.selectAll();
        }
        return roleMapper.findByUser( user.getId() );
    }
}
