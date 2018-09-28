package com.czxy.bos.elasticsearch.service;

import com.czxy.bos.elasticsearch.dao.BookRepository;
import com.czxy.bos.elasticsearch.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class BookService {

    @Resource
    private BookRepository bookRepository;

    public void save(Book book){
        this.bookRepository.save(book);
    }

    public Page<Book> findAll(Pageable pageable){
        return this.bookRepository.findAll(pageable);
    }

    public Page<Book> search(String title,Pageable pageable){
        return this.bookRepository.findByTitleLike(title,pageable);
    }
    public Page<Book> search(String title,String content,Pageable pageable){
        return this.bookRepository.findByTitleLikeAndContentLike(title,content,pageable);
    }

}
