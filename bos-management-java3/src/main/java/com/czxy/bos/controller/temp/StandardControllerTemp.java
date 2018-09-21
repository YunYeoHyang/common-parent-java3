package com.czxy.bos.controller.temp;

import com.czxy.bos.domain.base.Standard;
import com.czxy.bos.service.base.StandardService;
import com.czxy.bos.vo.EasyUIResult;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 收派标准的临时版，可以删除。用于与之后编写风格的对比
 * Created by liangtong on 2018/9/3.
 */
@Controller
@RequestMapping("/standardTemp")
public class StandardControllerTemp {

    @Resource
    private StandardService standardService;


    @RequestMapping("/queryStandaredByPageTemp")
    @ResponseBody
    public EasyUIResult<Standard> queryStandaredByPageTemp(Integer page , Integer rows){
        //1 查询获得分页数据
        PageInfo<Standard> pageInfo = standardService.queryStandardByPage(page ,rows);
        //2 封装数据
        return new EasyUIResult<>( pageInfo.getTotal() ,pageInfo.getList());
    }



}
