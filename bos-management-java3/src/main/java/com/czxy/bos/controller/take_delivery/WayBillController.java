package com.czxy.bos.controller.take_delivery;

import com.czxy.bos.domain.take_delivery.WayBill;
import com.czxy.bos.es.domain.EsWayBill;
import com.czxy.bos.service.take_delivery.WayBillService;
import com.czxy.bos.vo.EasyUIResult;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/wayBill")
public class WayBillController {

    @Resource
    private WayBillService wayBillService;

    /**
     * 分页查询
     * @param page
     * @param rows
     * @return
     */
    @GetMapping("/pageQuickQuery")
    public ResponseEntity<EasyUIResult<WayBill>> pageQuickQuery(Integer page , Integer rows){
        PageInfo<WayBill> pageInfo = this.wayBillService.pageQuickQuery(page, rows);
        EasyUIResult<WayBill> result = new EasyUIResult<>(pageInfo.getTotal() ,pageInfo.getList());
        return new ResponseEntity<EasyUIResult<WayBill>>(result , HttpStatus.OK);
    }

    /**
     * 添加订单
     * @param wayBill
     * @return
     */
    @RequiresPermissions( "waybill:add" )
    @PostMapping
    public ResponseEntity<String> saveWayBill(WayBill wayBill){
        wayBillService.saveWayBill(wayBill);
        return new ResponseEntity<>("运单录入成功" , HttpStatus.OK);
    }

    /**
     * 根据运单号查询
     * @param wayBillNum
     * @return
     */
    @GetMapping("/findByWayBillNum")
    public ResponseEntity<WayBill> findByWayBillNum(String wayBillNum) {
        // 调用业务层 查询
        WayBill wayBill = wayBillService.findByWayBillNum(wayBillNum);

        if (wayBill == null) {
            // 运单不存在
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            // 运单存在
            return new ResponseEntity<>(wayBill , HttpStatus.OK);
        }
    }

    /**
     * 运单列表显示需求
     * @param wayBill
     * @param page
     * @param rows
     * @return
     */
    @GetMapping("/pageQuery")
    public ResponseEntity<EasyUIResult<EsWayBill>> pageQuery(EsWayBill wayBill , Integer page , Integer rows){
        Page<EsWayBill> pageBean = this.wayBillService.pageQuery(wayBill , page, rows);
        EasyUIResult<EsWayBill> result = new EasyUIResult<>(pageBean.getTotalElements() ,pageBean.getContent());
        return new ResponseEntity<>(result , HttpStatus.OK);
    }
}
