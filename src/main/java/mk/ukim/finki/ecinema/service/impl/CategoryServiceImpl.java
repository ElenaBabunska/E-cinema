package mk.ukim.finki.ecinema.service.impl;

import mk.ukim.finki.ecinema.model.Category;
import mk.ukim.finki.ecinema.model.exceptions.CategoryIdNotFoundException;
import mk.ukim.finki.ecinema.repository.CategoryRepository;
import mk.ukim.finki.ecinema.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> listAll() {
        return this.categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) {
        return this.categoryRepository.findById(id)
                .orElseThrow(()->new CategoryIdNotFoundException(id));
    }

    @Override
    public Category create(String name) {
        return this.categoryRepository.save(new Category(name));
    }

    @Override
    public Category update(Long id, String name) {
        Category category = this.categoryRepository.findById(id).orElseThrow(() -> new CategoryIdNotFoundException(id));
        category.setName(name);
        return this.categoryRepository.save(category);
    }

    @Override
    public void delete(Long id) {
        this.categoryRepository.deleteById(id);
    }

    @Override
    public List<Category> searchCategories(String searchText) {
        return categoryRepository.findAllByNameLike(searchText);
    }
}
