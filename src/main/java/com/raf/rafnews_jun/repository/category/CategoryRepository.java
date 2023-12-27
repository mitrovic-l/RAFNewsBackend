package com.raf.rafnews_jun.repository.category;

import com.raf.rafnews_jun.entities.Category;

import java.util.List;

public interface CategoryRepository {
    List<Category> allCategories();
    Category addCategory(Category category);
    Category findCategory(Integer id);
    Category updateCategory(Category category, String name);
    void deleteCategory(Integer id);
}
