package com.czxy.bos.service.system;

import com.czxy.bos.dao.system.PermissionMapper;
import com.czxy.bos.domain.system.Permission;
import com.czxy.bos.domain.system.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class PermissionService {

    @Resource
    private PermissionMapper permissionMapper;

    /**
     * 查找指定用户授权的所有权限（如果是admin用户，将查询所有权限）
     * @param user
     * @return
     */
    public List<Permission> findByUser(User user) {
        if(user.getUsername().equals("admin")){
            return permissionMapper.selectAll();
        }
        return permissionMapper.findByUser(user.getId());
    }

}
