package com.czxy.bos.controller.transit;

import com.czxy.bos.domain.transit.InOutStorageInfo;
import com.czxy.bos.service.transit.InOutStorageInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/inoutstore")
public class InOutStorageInfoController {

    @Resource
    private InOutStorageInfoService inOutStorageInfoService;

    @PostMapping()
    public ResponseEntity<Void> save(InOutStorageInfo inOutStorageInfo){

        inOutStorageInfoService.save(inOutStorageInfo);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
