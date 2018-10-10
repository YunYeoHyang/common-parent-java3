package com.czxy.bos.controller.system;

import com.czxy.bos.domain.system.Menu;
import com.czxy.bos.domain.system.Permission;
import com.czxy.bos.service.system.PermissionService;
import com.czxy.bos.vo.EasyUIResult;
import com.github.pagehelper.PageInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    @GetMapping
    public ResponseEntity<EasyUIResult<Permission>> findPermissionList(Integer page , Integer rows){

        PageInfo<Permission> pageInfo = permissionService.findAllPermissionList(page ,rows);
        EasyUIResult<Permission> result = new EasyUIResult<>(pageInfo.getTotal() , pageInfo.getList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Permission>> findAll() {
        List<Permission> list = permissionService.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> savePermission(Permission permission){
        permissionService.savePermission(permission);
        return new ResponseEntity<>("添加成功" , HttpStatus.OK);
    }
}