package mk.ukim.finki.ecinema.service;

import mk.ukim.finki.ecinema.model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> listAll();

    Category findById(Long id);

    Category create(String name);

    Category update(Long id, String name);

    void delete(Long id);

    List<Category> searchCategories(String searchText);
}
