package com.czxy.bos.controller.base;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    /**
     * 访问第一层html页面
     * @param p
     * @return
     */
    @RequestMapping("/{path}")
    public String index(@PathVariable("path") String p){
        return p;
    }

    /**
     * 访问第二层html页面
     * @param p
     * @return
     */
    @RequestMapping("/pages/{dir}/{path}")
    public String index2(@PathVariable("dir") String d , @PathVariable("path") String p){
        return "/pages/" + d + "/" + p;
    }


}
