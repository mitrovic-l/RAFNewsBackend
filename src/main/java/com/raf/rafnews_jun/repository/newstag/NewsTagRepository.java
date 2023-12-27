package com.raf.rafnews_jun.repository.newstag;

public interface NewsTagRepository {
    void addNewsTag(Integer news, Integer tag);
    void deleteNewsTag(Integer news);
    void deleteTagFromNews(Integer news, Integer tag);
}
