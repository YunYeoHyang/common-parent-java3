package com.czxy.bos.controller.system;

import com.czxy.bos.domain.system.Role;
import com.czxy.bos.service.system.RoleService;
import com.czxy.bos.vo.EasyUIResult;
import com.github.pagehelper.PageInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<EasyUIResult<Role>> findAllRoleList(Integer page , Integer rows){
        PageInfo<Role> list = roleService.findAllRoleList(page ,rows);
        EasyUIResult result = new EasyUIResult(list.getTotal() ,list.getList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> saveRole(Role role,String menuIds,String [] permissionIds){

        roleService.saveRole(role,menuIds,permissionIds);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
