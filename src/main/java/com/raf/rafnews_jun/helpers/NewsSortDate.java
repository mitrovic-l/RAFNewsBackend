package com.raf.rafnews_jun.helpers;

import com.raf.rafnews_jun.entities.News;

import java.time.LocalDate;
import java.util.Comparator;

public class NewsSortDate implements Comparator<News> {

    @Override
    public int compare(News o1, News o2) {
        String[] o1Date = o1.getCreatedAt().split("-");
        String[] o2Date = o2.getCreatedAt().split("-");

        return LocalDate.of(Integer.parseInt(o1Date[0]), Integer.parseInt(o1Date[1]), Integer.parseInt(o1Date[2])).compareTo(LocalDate.of(Integer.parseInt(o2Date[0]), Integer.parseInt(o2Date[1]), Integer.parseInt(o2Date[2])));
    }
}
