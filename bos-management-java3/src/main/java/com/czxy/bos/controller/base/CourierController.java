package com.czxy.bos.controller.base;

import com.czxy.bos.domain.base.Courier;
import com.czxy.bos.service.base.CourierService;
import com.czxy.bos.vo.EasyUIResult;
import com.github.pagehelper.PageInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/courier")
public class CourierController {

    //注入service
    @Resource
    private CourierService courierService;

    /**
     * 添加快递员
     * @param courier
     * @return
     */
    @PostMapping
    public ResponseEntity<String> save( Courier courier ){
        int r = courierService.saveCourier(courier);
        if(r==1){

            return new ResponseEntity<String>("创建成功", HttpStatus.CREATED);  //201
        }
        return new ResponseEntity<String>("数据添加异常",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 查询功能
     * @param page
     * @param rows
     * @return
     */
    @GetMapping
    public ResponseEntity<EasyUIResult<Courier>> findAll( Integer page , Integer rows){
        //查询含有分页数据
        PageInfo<Courier> pageInfo = courierService.findAll(page ,rows);
        //封装datagrid需要数据
        EasyUIResult<Courier> result = new EasyUIResult( pageInfo.getTotal() , pageInfo.getList() );
        //封装含有状态码数据
        return new ResponseEntity<>( result , HttpStatus.OK );
    }

    /**
     * 作废功能
     * 注意：ajax直接提交一个数组，此项需要特殊处理，通过@RequestParam 确定名称
     * @param ids
     * @return
     */
    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam(name="ids[]") Integer[] ids){
        //删除
        int count = courierService.deleteCourier(ids);
        //提示
        return new ResponseEntity<String>("本次作废"+count+"条",HttpStatus.OK); //200通用删除，204 删除特有状态码
    }

    /**
     * 查询没有关联的快递员
     * @return
     */
    @GetMapping("/findNoAssociation")
    public ResponseEntity<List<Courier>> findNoAssociation(){
        //1 查询
        List<Courier> list = courierService.findNoAssociation();
        //2 封装
        return new ResponseEntity<List<Courier>>(list ,HttpStatus.OK);
    }


    @GetMapping("/findAssociationCourier")
    public ResponseEntity<List<Courier>> findAssociationCourier(String fixedAreaId){
        //1 查询
        List<Courier> list = courierService.findAssociationCourier(fixedAreaId);
        //2 封装
        return new ResponseEntity<List<Courier>>(list ,HttpStatus.OK);
    }

    /**
     * 还原功能
     * @param ids
     * @return
     */
    @PostMapping(value = "/restore")
    public ResponseEntity<Void> restore(@RequestParam("ids[]")Integer[] ids) {
        courierService.restore(ids);

        // 成功，返回204 ,操作成功，但是不返回数据
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

}
