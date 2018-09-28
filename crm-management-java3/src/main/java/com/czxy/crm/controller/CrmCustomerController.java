package com.czxy.crm.controller;

import com.czxy.crm.domain.Customer;
import com.czxy.crm.service.CrmCustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/crmCustomer")
public class CrmCustomerController {

    @Resource
    private CrmCustomerService crmCustomerService;

    /**
     * http://localhost:8090/crmCustomer/findNoAssociationCustomers
     * 查询没有关联定区的客户
     * @return
     */
    @GetMapping("/findNoAssociationCustomers")
    public ResponseEntity<List<Customer>> findNoAssociationCustomers(){
        //1 查询
        List<Customer> list = crmCustomerService.findNoAssociationCustomers();
        //2 封装状态码
        return new ResponseEntity<>( list , HttpStatus.OK);
    }

    /** http://localhost:8090/crmCustomer/findHasAssociationFixedAreaCustomers?fixedAreaId=dq001
     * 查询定区关联的客户
     * @param fixedAreaId
     * @return
     */
    @GetMapping("/findHasAssociationFixedAreaCustomers")
    public ResponseEntity<List<Customer>> findHasAssociationFixedAreaCustomers(String fixedAreaId){
        //1 查询
        List<Customer> list = crmCustomerService.findHasAssociationFixedAreaCustomers(fixedAreaId);
        //2 封装状态码
        return new ResponseEntity<List<Customer>>(list , HttpStatus.OK);
    }

    /** http://localhost:8090/crmCustomer/associationCustomersToFixedArea?fixedAreaId=dq001&customerIds=1,2
     * 定区关联一组客户
     * @param fixedAreaId
     * @param customerIds
     * @return
     */
    @PostMapping("/associationCustomersToFixedArea")
    public ResponseEntity<String> associationCustomersToFixedArea(String fixedAreaId , String customerIds){
        //操作
        crmCustomerService.associationCustomersToFixedArea(fixedAreaId , customerIds);
        //提示
        return new ResponseEntity<String>("操作成功",HttpStatus.OK);
    }

    /**
     *
     * @param customer  远程前端系统访问，发送的为json数据，需通过@RequestBody确定请求数据为json
     * @return
     */
    @PostMapping("/saveCustomer")
    public ResponseEntity<String> saveCustomer(@RequestBody  Customer customer){

        try {
            //通过service进行操作
            crmCustomerService.saveCustomer(customer);
            return new ResponseEntity<String>("注册成功",HttpStatus.CREATED); //201
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.ALREADY_REPORTED); //208
        }
    }

    /**
     * http://localhost:8090/customer/telephone?telephone=136...
     * @param telephone
     * @return
     */
    @GetMapping("findByTelephone")
    public ResponseEntity<Customer> findByTelephone(String telephone){
        //1查询
        Customer customer = crmCustomerService.findByTelephone(telephone);
        //2 封装
        return new ResponseEntity<Customer>(customer ,HttpStatus.OK);

    }

    /**
     * 激活
     * @param telephone
     * @return
     */
    @GetMapping("/updateType")
    public ResponseEntity<String> updateType(String telephone){
        try {
            //1 更新
            crmCustomerService.updateType(telephone);
            //2 提示
            return new ResponseEntity<String>("账号激活成功",HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("账号激活失败",HttpStatus.ALREADY_REPORTED);
        }
    }

    /**
     * 通过手机号和密码进行客户查询
     *  http://localhost:8090/crmCustomer/findCustomerTelephoneAndPassword?telephone=13612345678&password=12345
     * @param telephone
     * @param password
     * @return
     */
    @GetMapping("/findCustomerTelephoneAndPassword")
    public ResponseEntity<Customer> findCustomerTelephoneAndPassword(String telephone , String password){
        //1 查询
        Customer customer = this.crmCustomerService.findCustomerTelephoneAndPassword( telephone ,password );
        //2 封装
        return new ResponseEntity<Customer>(customer , HttpStatus.OK);
    }

    /**
     * 通过地址和客户id查询定区id
     * @param address
     * @param customerId
     * @return
     */
    @GetMapping("/findFixedAreaIdByAddressAndID")
    public ResponseEntity<String> findFixedAreaIdByAddressAndID(String address , String customerId){
        String fixedAreaId = crmCustomerService.findFixedAreaIdByAddressAndID(address , customerId);
        return new ResponseEntity<>(fixedAreaId,HttpStatus.OK);
    }
}
