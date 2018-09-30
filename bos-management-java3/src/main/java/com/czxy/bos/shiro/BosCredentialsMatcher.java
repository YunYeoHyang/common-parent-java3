package com.czxy.bos.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;

public class BosCredentialsMatcher extends SimpleCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token , AuthenticationInfo info){

        // 向下转型
        UsernamePasswordToken upToken = (UsernamePasswordToken)token;

        // 获取用户页面输入的密码
        String pwd = new String(upToken.getPassword());

        // 加密
        String newPwd = new Md5Hash(pwd, upToken.getUsername(), 2).toString();

        // 获取数据库密码
        String dbPwd = info.getCredentials().toString();

        return equals( newPwd , dbPwd );
    }
}
