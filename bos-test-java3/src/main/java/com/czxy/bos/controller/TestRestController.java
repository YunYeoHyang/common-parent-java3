package com.czxy.bos.controller;

import org.springframework.web.bind.annotation.*;

/**
 * Created by liangtong on 2018/9/3.
 */
@RestController
@RequestMapping("/mytest")
public class TestRestController {

    /**
     * http://localhost:8086/mytest
     * 需要注意：请求方式必须是get
     * @return
     */
    @GetMapping
    public String demo(){
        System.out.println("get");
        return null;
    }

    /**
     * http://localhost:8086/mytest
     * 需要注意：请求方式必须是post
     * @return
     */
    @PostMapping
    public String demo2(){
        System.out.println("post");
        return null;
    }

    /**
     * http://localhost:8086/mytest
     * 需要注意：请求方式必须是put
     * @return
     */
    @PutMapping
    public String demo3(){
        System.out.println("put");
        return null;
    }
    /**
     * http://localhost:8086/mytest
     * 需要注意：请求方式必须是delete
     * @return
     */
    @DeleteMapping
    public String demo4(){
        System.out.println("delete");
        return null;
    }

    /** 通过id查询时，建议在路径后面追加子路径，最后的内容就是id
     * http://localhost:8086/mytest/12345
     * @return
     */
    @GetMapping(value="/{id}")
    public String demo5(@PathVariable("id") Integer id ){
        System.out.println("id : " + id);
        return null;
    }

}
