package uz.pdp.service;

import uz.pdp.model.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CategoryService implements BaseService<String, Category, List<Category>> {
    List<Category> categoryList = new ArrayList<>();

    @Override
    public boolean add(Category category) {
        categoryList.add(category);
        return true;
    }

    @Override
    public Category check(String categoryName) {
        for (Category category: categoryList) {
            if(category.getName().equals(categoryName))
                return category;
        }
        return null;
    }

    @Override
    public Category check(String t1, String t2) {
        return null;
    }

    @Override
    public List<Category> list(UUID id) {
        List<Category> categories = new ArrayList<>();
        for(Category category: categoryList){
            if(category.getParentCategoryId().equals(id))
                categories.add(category);
        }
        return categories;
    }
}
