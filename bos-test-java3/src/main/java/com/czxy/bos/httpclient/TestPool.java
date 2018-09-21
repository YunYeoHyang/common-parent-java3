package com.czxy.bos.httpclient;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by liangtong on 2018/9/10.
 */
public class TestPool {

    public static void main(String[] args) {
        //0.1 配置连接管理
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        // 1) 最大连接数
        cm.setMaxTotal(1000);
        // 2) 并发访问数
        cm.setDefaultMaxPerRoute(20);

        //0.2 请求配置
        RequestConfig rc = RequestConfig.custom()
                .setConnectTimeout(1000)
                .setConnectionRequestTimeout(500)
                .setSocketTimeout(10 * 1000)
                .build();

        //1.1 获得工具类（打开自定义浏览器，配置连接池）
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .setDefaultRequestConfig(rc)
                .setRedirectStrategy(new LaxRedirectStrategy())
                .build();

        //1.2 获得连接工厂
        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

        //1.3 通过工厂获得模板
        RestTemplate template = new RestTemplate(requestFactory);
        List<HttpMessageConverter<?>> list = template.getMessageConverters();
        for (HttpMessageConverter<?> mc : list) {
            if (mc instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) mc).setDefaultCharset(Charset.forName("UTF-8"));
            }
        }

        //2 发送get请求
        ResponseEntity<String> responseEntity = template.getForEntity("http://localhost:8090/customer/associationFixedAreaCustomers?fixedAreaId=dq001",String.class);
        //template.postForEntity()
        //template.put("",null);
        //template.delete();

        System.err.println(responseEntity);

    }
}
