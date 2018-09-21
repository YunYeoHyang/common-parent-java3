package com.czxy.bos.controller.base;

import com.czxy.bos.domain.base.TakeTime;
import com.czxy.bos.service.base.TakeTimeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/takeTime")
public class TakeTimeController {

    @Resource
    private TakeTimeService takeTimeService;

    @GetMapping("/findAll")
    public ResponseEntity<List<TakeTime>> findAll(){
        //1 查询所有
        List<TakeTime> list = takeTimeService.findAll();
        //2 封装
        return new ResponseEntity<List<TakeTime>>(list , HttpStatus.OK);
    }


}
