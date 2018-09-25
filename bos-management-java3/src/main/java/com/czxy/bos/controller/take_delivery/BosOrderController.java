package com.czxy.bos.controller.take_delivery;

import com.czxy.bos.domain.base.Area;
import com.czxy.bos.domain.base.Courier;
import com.czxy.bos.domain.take_delivery.Order;
import com.czxy.bos.service.base.AreaService;
import com.czxy.bos.service.base.CourierService;
import com.czxy.bos.service.take_delivery.BosOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bosOrder")
public class BosOrderController {

    @Resource
    private BosOrderService bosOrderService;

    @Resource
    private AreaService areaService;

    @Resource
    private CourierService courierService;

    @Resource
    RestTemplate restTemplate;


    /**
     * 下单
     * @param order
     * @return
     */
    @PostMapping
    public ResponseEntity<String> add(@RequestBody Order order){
        //1 处理数据（自动生成）
        //1.1 订单号
        order.setOrderNum(UUID.randomUUID().toString());
        //1.2 时间
        order.setOrderTime(new Date());
        //1.3 状态 1 待取件 2 运输中 3 已签收 4 异常
        order.setStatus("1");
        //1.4 地区 （前端系统统计省市县，但没有area表中id）
        Area sendArea = areaService.selectByArea( order.getSendArea() );
        order.setSendAreaId( sendArea.getId() );

        Area recArea = areaService.selectByArea( order.getRecArea() );
        order.setRecAreaId( recArea.getId());


        //2 自动分单
        //2.1 CRM地址完全匹配（客户订单中的发货地址，和客户表记录地址信息是一致，客户-->定区-->快递员）
        //2.1  1) 远程调用crm系统，通过客户发送地址和客户id，查询定区
        String url = "http://localhost:8090/crmCustomer/findFixedAreaIdByAddressAndID?address="+order.getSendAddress()+"&customerId=" + order.getCustomer_id();
        System.out.println(
                url

        );
        ResponseEntity<String> entity = restTemplate.getForEntity(url,String.class);
        String fixedAreaId = entity.getBody();
        //2.1  2) 查询定区管理的所有的快递员
        List<Courier> courierList = courierService.findAssociationCourier(fixedAreaId);
        //2.1  3) 分单--将订单关联一下快递员
        for(Courier courier : courierList){
            if(courier != null){
                //将订单和快递员关联
                order.setCourierId(courier.getId());
                //自动分单成功
                order.setOrderType("1");
                //保存
                bosOrderService.addOrder(order);
                return new ResponseEntity<String>("创建订单成功，并已经成功自动分单！",HttpStatus.OK);
            }
        }

        //3 保存（人工分单）--订单状态 ， roderType==2   (1 自动分单 2 人工分单)
        order.setOrderType("2");
        bosOrderService.addOrder(order);


        //4 生成工单

        return  new ResponseEntity<String>("添加成功", HttpStatus.OK);
    }
}
