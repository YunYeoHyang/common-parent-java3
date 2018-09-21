package com.czxy.bos.redis;

import redis.clients.jedis.Jedis;

/**
 * Created by liangtong on 2018/9/13.
 */
public class TestJedis {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1",6379);
        //获得数据
        String username = jedis.get("username");
        System.out.println(username);

//        jedis.set("username" , "rose");

    }
}
