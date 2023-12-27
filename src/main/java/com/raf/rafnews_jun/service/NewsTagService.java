package com.raf.rafnews_jun.service;

import com.raf.rafnews_jun.repository.newstag.NewsTagRepository;

import javax.inject.Inject;

public class NewsTagService {
    @Inject
    NewsTagRepository newsTagRepository;

    public void insert(Integer news, Integer tag) {
        this.newsTagRepository.addNewsTag(news, tag);
    }
    public void delete(Integer news){
        this.newsTagRepository.deleteNewsTag(news);
    }
    public void deleteTag(Integer news, Integer tag){
        this.newsTagRepository.deleteTagFromNews(news, tag);
    }
}
