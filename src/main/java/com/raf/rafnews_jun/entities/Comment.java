package com.raf.rafnews_jun.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Comment {
    private Integer id;
    @NotNull(message = "Title field is required")
    @NotEmpty(message = "Title field is required")
    private String author;
    @NotNull(message = "Title field is required")
    @NotEmpty(message = "Title field is required")
    private String text;
    private String postedAt;
    private Integer news_id;

    public Comment() {
    }

    public Comment(String author, String text, String postedAt) {
        this();
        this.author = author;
        this.text = text;
        this.postedAt = postedAt;
    }

    public Comment(String author, String text, String postedAt, Integer news_id) {
        this(author, text, postedAt);
        this.news_id = news_id;
    }

    public Comment(Integer id, String author, String text, String postedAt, Integer news_id) {
        this(author, text, postedAt, news_id);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(String postedAt) {
        this.postedAt = postedAt;
    }

    public Integer getNews_id() {
        return news_id;
    }

    public void setNews_id(Integer news_id) {
        this.news_id = news_id;
    }
}
