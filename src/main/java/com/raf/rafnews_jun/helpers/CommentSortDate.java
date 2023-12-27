package com.raf.rafnews_jun.helpers;

import com.raf.rafnews_jun.entities.Comment;

import java.time.LocalDate;
import java.util.Comparator;

public class CommentSortDate implements Comparator<Comment> {
    @Override
    public int compare(Comment o1, Comment o2) {
        String[] o1Date = o1.getPostedAt().split("-");
        String[] o2Date = o2.getPostedAt().split("-");

        return LocalDate.of(Integer.parseInt(o1Date[0]), Integer.parseInt(o1Date[1]), Integer.parseInt(o1Date[2])).compareTo(LocalDate.of(Integer.parseInt(o2Date[0]), Integer.parseInt(o2Date[1]), Integer.parseInt(o2Date[2])));
    }
}
