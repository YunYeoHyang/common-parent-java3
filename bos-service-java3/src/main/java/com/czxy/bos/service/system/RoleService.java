package com.czxy.bos.service.system;

import com.czxy.bos.dao.system.RoleMapper;
import com.czxy.bos.dao.system.RoleMenuMapper;
import com.czxy.bos.dao.system.RolePermissionMapper;
import com.czxy.bos.domain.system.Role;
import com.czxy.bos.domain.system.RoleMenu;
import com.czxy.bos.domain.system.RolePermission;
import com.czxy.bos.domain.system.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private RolePermissionMapper rolePermissionMapper;

    /**
     * 查询所有的角色
     */
    public PageInfo<Role> findAllRoleList(Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        List<Role> list = roleMapper.selectAll();
        return new PageInfo<>(list);
    }

    /**
     * 查找指定用户授权的所有角色 （如果是admin用户，将查询所有角色）
     *
     * @param user
     * @return
     */
    public List<Role> findByUser(User user) {

        if (user.getUsername().equals("admin")) {
            return roleMapper.selectAll();
        }
        return roleMapper.findByUser(user.getId());
    }

    /**
     * 此处的操作如下：
     * 1 保存role
     * 2 建立role和menu的关系
     * 3 建立role和permission的关系
     */
    public void saveRole(Role role, String menuIds, String[] permissionIds) {

        // 1 保存 Role
        roleMapper.insert(role);

        // 2 建立 Role 和 Menu 的关系
        String[] menus = menuIds.split(",");
        for (String id :
                menus) {
            RoleMenu rm = new RoleMenu();
            rm.setRoleId(role.getId());
            rm.setMenuId(Integer.parseInt(id));
            roleMenuMapper.insert(rm);
        }

        // 3 建立 Role 和 Permission 的关系
        for (String id :
                permissionIds) {
            RolePermission rp = new RolePermission();
            rp.setRoleId(role.getId());
            rp.setPermissionId(Integer.parseInt(id));
            rolePermissionMapper.insert(rp);
        }
    }
}
