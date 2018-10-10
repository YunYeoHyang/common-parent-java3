package com.czxy.bos.controller.transit;

import com.czxy.bos.domain.transit.TransitInfo;
import com.czxy.bos.service.transit.TransitInfoService;
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
@RequestMapping("/transit")
public class TransitInfoController {

    @Resource
    private TransitInfoService transitInfoService;

    @PostMapping
    public ResponseEntity<Void> create(String wayBillIds){
        // 调用业务层 ， 保存 transitInfo 信息
        transitInfoService.createTransits(wayBillIds);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<EasyUIResult<TransitInfo>> pageQuery(Integer page,Integer rows) {
        // 查询用户
        PageInfo<TransitInfo> pageInfo = this.transitInfoService.pageQuery(page, rows);
        // 封装
        EasyUIResult<TransitInfo> result = new EasyUIResult<>(pageInfo.getTotal() , pageInfo.getList());
        // 封装状态码
        return  new ResponseEntity<>(result, HttpStatus.OK);

    }
}
