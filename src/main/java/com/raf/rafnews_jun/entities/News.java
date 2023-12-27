package com.raf.rafnews_jun.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class News {
    private Integer id;
    @NotNull(message = "Title field is required")
    @NotEmpty(message = "Title field is required")
    private String title;
    @NotNull(message = "Title field is required")
    @NotEmpty(message = "Title field is required")
    private String author;
    @NotNull(message = "Title field is required")
    @NotEmpty(message = "Title field is required")
    private String content;
    private String createdAt;
    private Integer views;
    private Integer category_id;
    private Integer user_id;
    private List<Tag> tags;
    private String categoryName;
    private List<Comment> comments;
    private String searchedTag;

    public News() {
        tags = new ArrayList<>();
        comments = new ArrayList<>();
    }

    public News(Integer id, String title, String author, String content, String createdAt) {
        this();
        this.id = id;
        this.title = title;
        this.author = author;
        this.content = content;
        this.createdAt = createdAt;
    }

    public News(Integer id, String title, String author, String content, String createdAt, Integer views, Integer category_id) {
        this(id, title, author, content, createdAt);
        this.views = views;
        this.category_id = category_id;
    }

    public News(Integer id, String title, String author, String content, String createdAt, Integer views, Integer category_id, Integer user_id) {
        this(id, title, author, content, createdAt, views, category_id);
        this.user_id = user_id;
    }

    public News(Integer id, String title, String author, String content, String createdAt, Integer views, Integer category_id, Integer user_id, List<Tag> tags) {
        this();
        this.tags = tags;
    }

    public String getSearchedTag() {
        return searchedTag;
    }

    public void setSearchedTag(String searchedTag) {
        this.searchedTag = searchedTag;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
}
