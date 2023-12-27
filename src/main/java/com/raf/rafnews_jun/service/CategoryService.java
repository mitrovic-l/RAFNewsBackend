package com.raf.rafnews_jun.service;

import com.raf.rafnews_jun.entities.Category;
import com.raf.rafnews_jun.repository.category.CategoryRepository;

import javax.inject.Inject;
import java.util.List;

public class CategoryService {
    @Inject
    CategoryRepository categoryRepository;

    public List<Category> allCategories(){
        return this.categoryRepository.allCategories();
    }
    public Category addCategory(Category category){
        return this.categoryRepository.addCategory(category);
    }
    public Category findCategory(Integer id){
        return this.categoryRepository.findCategory(id);
    }
    public Category updateCategory(Category category, String name){
        return this.categoryRepository.updateCategory(category, name);
    }
    public void deleteCategory(Integer id){
        this.categoryRepository.deleteCategory(id);
    }
}
