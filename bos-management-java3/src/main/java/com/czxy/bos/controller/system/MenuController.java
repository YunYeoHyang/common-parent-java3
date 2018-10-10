package com.czxy.bos.controller.system;

import com.czxy.bos.domain.system.Menu;
import com.czxy.bos.service.system.MenuService;
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
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private MenuService menuService;

    /**
     * 查询所有
     * @param page
     * @param rows
     * @return
     */
    @GetMapping
    public ResponseEntity<EasyUIResult<Menu>> findAll(Integer page , Integer rows){
        PageInfo<Menu> pageInfo = menuService.findAll(page ,rows);
        EasyUIResult<Menu> result = new EasyUIResult<>(pageInfo.getTotal(), pageInfo.getList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> addMenu(Menu menu){
        menuService.save(menu);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Menu>> findAll(){
        List<Menu> list = menuService.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
