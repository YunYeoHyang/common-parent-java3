package com.czxy.bos.elasticsearch.dao;

import com.czxy.bos.elasticsearch.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface BookRepository extends ElasticsearchRepository<Book , Integer> {

    Page<Book> findByTitleLike(String title, Pageable pageable);
    Page<Book> findByTitleLikeAndContentLike(String title, String content, Pageable pageable);

}
