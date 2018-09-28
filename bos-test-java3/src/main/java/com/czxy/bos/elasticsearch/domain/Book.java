package com.czxy.bos.elasticsearch.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "book",type = "bookType")
public class Book {

    @Id
    private Integer id;
    private String title;
    private String content;

    public Book(Integer id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public Book() {
    }
}
