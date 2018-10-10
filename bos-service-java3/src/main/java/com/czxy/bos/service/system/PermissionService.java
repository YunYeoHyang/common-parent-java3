package com.czxy.bos.service.system;

import com.czxy.bos.dao.system.PermissionMapper;
import com.czxy.bos.domain.system.Menu;
import com.czxy.bos.domain.system.Permission;
import com.czxy.bos.domain.system.User;
import com.czxy.bos.exception.BosException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

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

    /**
     * 查询所有
     * @param page
     * @param rows
     * @return
     */
    public PageInfo<Permission> findAllPermissionList(Integer page , Integer rows) {
        PageHelper.startPage( page ,rows);
        List<Permission> list = permissionMapper.selectAll();
        return new PageInfo<>( list );
    }

    public List<Permission> findAll() {
        return permissionMapper.selectAll();
    }

    public void savePermission(Permission permission) {
        //校验【名称】唯一
        Example example = new Example(Permission.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("name", permission.getName());
        Permission temp = permissionMapper.selectOneByExample(example);
        if(temp != null){
            throw new BosException("权限名称已存在");
        }
        //校验【关键字】唯一
        Example example2 = new Example(Permission.class);
        Example.Criteria criteria2 = example2.createCriteria();
        criteria2.andEqualTo("keyword", permission.getKeyword());
        temp = permissionMapper.selectOneByExample(example2);
        if(temp != null){
            throw new BosException("关键字已存在");
        }

        permissionMapper.insert(permission);
    }
}
