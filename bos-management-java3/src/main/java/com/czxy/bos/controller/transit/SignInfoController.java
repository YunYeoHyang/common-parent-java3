package com.czxy.bos.controller.transit;

import com.czxy.bos.domain.transit.SignInfo;
import com.czxy.bos.service.transit.SignInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/sign")
public class SignInfoController {

    @Resource
    private SignInfoService signInfoService;

    @PostMapping()
    public ResponseEntity<Void> save(String transitInfoId, SignInfo signInfo) {
        signInfoService.save(transitInfoId, signInfo);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
