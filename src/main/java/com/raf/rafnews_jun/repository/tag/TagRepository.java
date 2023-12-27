package com.raf.rafnews_jun.repository.tag;

import com.raf.rafnews_jun.entities.Tag;

import java.util.List;

public interface TagRepository {
    List<Tag> allTags();
    Tag addTag(Tag tag);
    Tag findTag(String tag);
    Tag findTagById(Integer id);
    void deleteTag(Integer id);
}
