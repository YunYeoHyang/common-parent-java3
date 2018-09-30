package com.czxy.bos.shiro;

import com.czxy.bos.domain.system.Permission;
import com.czxy.bos.domain.system.Role;
import com.czxy.bos.domain.system.User;
import com.czxy.bos.service.system.PermissionService;
import com.czxy.bos.service.system.RoleService;
import com.czxy.bos.service.system.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.List;

public class BosRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @Resource
    private PermissionService permissionService;


    /**
     * 授权
     * @param pc
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
        System.out.println("授权");

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        // 根据当前登陆用户 查询对应角色和权限
        User user = (User) pc.getPrimaryPrincipal();

        // 调用业务层，查询角色
        List<Role> roles = roleService.findByUser(user);

        for ( Role role : roles){
            authorizationInfo.addRole( role.getKeyword() );
        }

        // 调用业务层，查询权限
        List<Permission> permissions = permissionService.findByUser(user);
        for (Permission permission : permissions ){
            authorizationInfo.addStringPermission( permission.getKeyword() );
        }

        return authorizationInfo;
    }

    /**
     * 认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("认证");

        // 1. 获得密码
        String userName = (String) token.getPrincipal();

        // 2. 通过 userName 从数据库中查找 User 对象，如果找到，没找到
        User user = userService.findUserByUsername(userName);
        if (user == null){

            return null;
        }
        return new SimpleAuthenticationInfo( user , user.getPassword() , getName() );
    }
}
