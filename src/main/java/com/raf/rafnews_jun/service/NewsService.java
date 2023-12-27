package com.raf.rafnews_jun.service;

import com.raf.rafnews_jun.entities.News;
import com.raf.rafnews_jun.repository.news.NewsRepository;

import javax.inject.Inject;
import java.util.List;

public class NewsService {
    @Inject
    NewsRepository newsRepository;

    public List<News> allNews(){
        return this.newsRepository.allNews();
    }
    public List<News> allNewsByViews(){
        return this.newsRepository.allNewsByViews();
    }
    public List<News> allNewsInCategory(Integer category_id){
        return this.newsRepository.allNewsInCategory(category_id);
    }
    public List<News> allNewsWithTag(Integer id){
        return this.newsRepository.allNewsWithTag(id);
    }
    public News addNews(News news){
        return this.newsRepository.addNews(news);
    }
    public News updateNews(News news, Integer id){
        return this.newsRepository.updateNews(news, id);
    }
    public News findNews(Integer id){
        return this.newsRepository.findNews(id);
    }
    public News findNewsForCreator(Integer id) { return  this.newsRepository.findNewsForCreator(id); }
    public void deleteNews(Integer id){
        this.newsRepository.deleteNews(id);
    }
}
