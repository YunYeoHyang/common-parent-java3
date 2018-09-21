package com.czxy.bos.httpclient;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by liangtong on 2018/9/10.
 */
public class TestGet {

    public static void main(String[] args) throws IOException {
        // 创建Httpclient对象,相当于打开了浏览器
        CloseableHttpClient httpclient = HttpClients.createDefault();

        // 创建HttpGet请求，相当于在浏览器输入地址
        HttpGet httpGet = new HttpGet("http://www.baidu.com/");

        // 执行请求，相当于敲完地址后按下回车。获取响应
        CloseableHttpResponse response =  httpclient.execute(httpGet);
        // 判断返回状态是否为200
        if (response.getStatusLine().getStatusCode() == 200) {
            // 解析响应，获取数据
            String content = EntityUtils.toString(response.getEntity(), "UTF-8");
            System.out.println(content);
        }

    }


}
