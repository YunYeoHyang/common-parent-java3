package com.czxy.bos.controller.base;

import com.czxy.bos.domain.base.FixedArea;
import com.czxy.bos.service.base.FixedAreaService;
import com.czxy.bos.vo.EasyUIResult;
import com.github.pagehelper.PageInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@RequestMapping("/fixedArea")
public class FixedAreaController {

    @Resource
    private FixedAreaService fixedAreaService;

    @Resource
    private RestTemplate restTemplate;

    /**
     * 添加定区
     * @param fixedArea
     * @return
     */
    @PostMapping
    public ResponseEntity<String> save(FixedArea fixedArea){
        //添加
        int count  = fixedAreaService.saveFixedArea(fixedArea);
        //提示
        if(count  == 1){
            return new ResponseEntity<String>("添加成功" , HttpStatus.CREATED); //201
        }
        return new ResponseEntity<String>("添加失败", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 分页条件查询    /fixedArea?page=3&rows=10
     *                /fixedArea 如果参数中没有page和rows，将使用默认值 @RequestParam(defaultValue="1")
     * @param fixedArea
     * @param page
     * @param rows
     * @return
     */
    @GetMapping
    public ResponseEntity<EasyUIResult<FixedArea>> findAll(FixedArea fixedArea,
                                                           @RequestParam(name="page",defaultValue="1") Integer page ,
                                                           @RequestParam(name="rows",defaultValue = "5")Integer rows){
        //1 查询PageInfo
        PageInfo<FixedArea> pageInfo = fixedAreaService.findAllByPage( fixedArea , page , rows );
        //2 result
        EasyUIResult result = new EasyUIResult( pageInfo.getTotal() , pageInfo.getList() );
        //3 status
        return new ResponseEntity<>( result ,HttpStatus.OK);
    }

    @GetMapping("/findNoAssociationCustomers")
    public ResponseEntity<String> findNoAssociationCustomers(){
        //请求crm项目，获得所有未关联定区的客户
        ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:8090/crmCustomer/findNoAssociationCustomers",String.class);
        //返回
        return entity;

    }

    @GetMapping("/findHasAssociationFixedAreaCustomers")
    public ResponseEntity<String> findHasAssociationFixedAreaCustomers(String fixedAreaId){
        //向crm系统发送请求
        return restTemplate.getForEntity("http://localhost:8090/crmCustomer/findHasAssociationFixedAreaCustomers?fixedAreaId=" + fixedAreaId ,String.class);
    }

    @PostMapping("/associationCustomersToFixedArea")
    public ResponseEntity<String> associationCustomersToFixedArea(String fixedAreaId, String customerIds){
        //向crm系统发送post请求
        return restTemplate.postForEntity("http://localhost:8090/crmCustomer/associationCustomersToFixedArea?fixedAreaId="+fixedAreaId+"&customerIds=" + customerIds ,null , String.class);
    }

    @PostMapping("/associationCourierToFixedArea")
    public ResponseEntity<String> associationCourierToFixedArea(String fixedAreaId , Integer courierId, Integer takeTimeId){
        //1 关联操作
        fixedAreaService.associationCourierToFixedArea(fixedAreaId, courierId , takeTimeId);
        //2 提示
        return new ResponseEntity<String>("操作成功",HttpStatus.OK);
    }

}
