package com.czxy.bos.util;

import java.util.Random;

public class GetRandomCodeUtil {
	 /** 随机生成4位验证码*/
    public static String getNumber(){
    	int number;//定义两变量
        Random ne = new Random();//实例化一个random的对象ne
        number = ne.nextInt(9999-1000+1)+1000;//为变量赋随机值1000-9999
        // System.out.println("产生的随机数是:"+number);//输出
        return String.valueOf(number);
    }
}
