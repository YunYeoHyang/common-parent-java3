package com.czxy.bos;

import com.czxy.bos.elasticsearch.domain.Book;
import com.czxy.bos.elasticsearch.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class TestElasticsearchService {

//    @Resource
//    private BookService bookService;
//
//    @Test
//    public void save(){
//        Book book = new Book(3,"PHP基础","PHP语法学习");
//        bookService.save(book);
//        System.out.println("保存数据");
//    }
//
//    @Test
//    public void findAll(){
//        Pageable pageable = PageRequest.of(0,10);
//        Page<Book> all = bookService.findAll(pageable);
//        System.out.println(all.getTotalPages());
//        for(Book book : all.getContent()){
//            System.out.println(book);
//        }
//    }
//
//    @Test
//    public void findAll2(){
//        Pageable pageable = PageRequest.of(0,10);
//        Page<Book> all = bookService.search("java",pageable);
//        System.out.println(all.getTotalPages());
//        for(Book book : all.getContent()){
//            System.out.println(book);
//        }
//    }
//
//    @Test
//    public void findAll3(){
//        Pageable pageable = PageRequest.of(0,10);
//        Page<Book> all = bookService.search("PHP","PHP",pageable);
//        System.out.println(all.getTotalPages());
//        for(Book book : all.getContent()){
//            System.out.println(book);
//        }
//    }

}
