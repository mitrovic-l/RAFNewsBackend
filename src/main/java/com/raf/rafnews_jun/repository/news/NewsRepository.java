package com.raf.rafnews_jun.repository.news;

import com.raf.rafnews_jun.entities.News;

import java.util.List;

public interface NewsRepository {
    List<News> allNews();
    List<News> allNewsByViews();
    List<News> allNewsInCategory(Integer category_id);
    List<News> allNewsWithTag(Integer id);
    News addNews(News news);
    News updateNews(News news, Integer id);
    News findNews(Integer id);
    News findNewsForCreator(Integer id); //nece se povecavati broj pregleda
    void deleteNews(Integer id);

}
