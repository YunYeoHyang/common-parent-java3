package com.czxy.bos.controller.base;

import com.czxy.bos.domain.base.Standard;
import com.czxy.bos.service.base.StandardService;
import com.czxy.bos.vo.EasyUIResult;
import com.github.pagehelper.PageInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController     //替换2个 @Controller @ResponseBody
@RequestMapping("/standard")
public class StandardController {

    @Resource
    private StandardService standardService;

    /**
     * 每一个方法都需要确定请求方式
     *      get：查 ， RequestMethod.GET
     *      post：增 ， RequestMethod.POST
     *      put：改 ， RequestMethod.PUT
     *      delete：删 ， RequestMethod.DELETE
     *  每一个@RequestMapping value可以不写了，通常可以完成“增删改查”操作。
     *      http://localhost:8088/standard  同一个路径，根据“请求方式不同”，可以完成不同的操作(及执行不同的方式)
     *      之前的方法上编写的方式
     *          @RequestMapping(method=RequestMethod.GET) get请求
     *          @RequestMapping(method=RequestMethod.POST) post请求
     *          @RequestMapping(method=RequestMethod.PUT) put请求
     *          @RequestMapping(method=RequestMethod.DELETE) delete请求
     *       之后可以采用简化版
     *          @XxxMapping
     *          @GetMapping 等效  @RequestMapping(method=RequestMethod.GET)
     *          @PostMapping
     *          @PutMapping
     *          @DeleteMapping
     *  如果有特殊请求，仍需要编写value值，确定名称。
     *
     *  建议：每一个方法返回时，应该携带响应状态
     *      200 ：正常 , HttpStatus.OK
     *      201 ：表示创建 , HttpStatus.CREATED
     *
     *      提供一个封装数据
     *          ResponseEntity<>(数据， 状态)
     */

    /**
     * http://localhost:8088/standard?page=1&rows=2
     * 查询所有
     *
     * @param page
     * @param rows
     * @return
     */
    //@RequestMapping(method=RequestMethod.GET)
    @GetMapping
    public ResponseEntity<EasyUIResult<Standard>> queryStandaredByPageTemp(Integer page, Integer rows) {
        //1 查询获得分页数据
        PageInfo<Standard> pageInfo = standardService.queryStandardByPage(page, rows);
        //2 封装数据
        EasyUIResult result = new EasyUIResult<>(pageInfo.getTotal(), pageInfo.getList());
        //3 返回含有状态的信息
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 添加收派标准
     *
     * @param standard
     * @return
     */
    @PostMapping
    public ResponseEntity<String> save(Standard standard) {

        try {
            //保存
            int r = standardService.saveStandard(standard);
            if (r == 1) { //表示添加1条
                return new ResponseEntity<String>("创建成功", HttpStatus.OK);   //200
            }
            return new ResponseEntity<String>("创建过程出现异常", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("创建失败", HttpStatus.INTERNAL_SERVER_ERROR); //500
        }

    }

    @PutMapping
    public ResponseEntity<String> update(Standard standard) {
        try {
            //更新操作
            int r = standardService.updateStandard(standard);
            if (r == 1) {
                return new ResponseEntity<String>("更新成功", HttpStatus.OK);
            }
            return new ResponseEntity<String>("更新过程出现异常", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("更新失败", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<String> delete(String ids) {
//        try {
        //1 将拼凑好的字符串，拆分成数组
        String[] arr = ids.split(",");
        //2 删除
        standardService.deleteStandard(arr);
        //3 提示
        return new ResponseEntity<String>("删除成功", HttpStatus.OK);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<String>("删除失败",HttpStatus.INTERNAL_SERVER_ERROR);
//        }
    }


    @GetMapping("/all")
    public ResponseEntity<List<Standard>> all() {
        //查询
        List<Standard> list = standardService.findAll();
        //封装
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


}
