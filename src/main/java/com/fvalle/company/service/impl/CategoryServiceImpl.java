package com.fvalle.company.service.impl;

import com.fvalle.company.dto.CategoryDto;
import com.fvalle.company.entity.Category;
import com.fvalle.company.exception.NotFoundException;
import com.fvalle.company.mapper.CategoryMapper;
import com.fvalle.company.repository.CategoryRepository;
import com.fvalle.company.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements ICategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    private static final int SIZE_DEFAULT = 10;

    @Override
    public List<CategoryDto> getAll() {
        List<Category> categoryList = (List<Category>) categoryRepository.findAll();
        return categoryMapper.toCategories(categoryList);
    }

    @Override
    public Optional<CategoryDto> getCategory(int categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if(!category.isPresent()){
            throw new NotFoundException("Error, id cannot be found");
        }else{
            return category.map(c -> categoryMapper.toCategoryDto(c));
        }
        //return categoryRepository.findById(categoryId).map(category -> categoryMapper.toCategoryDto(category));
    }

    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        Category category = categoryMapper.toCategory(categoryDto);
        return categoryMapper.toCategoryDto(categoryRepository.save(category));
    }

    @Override
    public boolean delete(int categoryId) {
        return getCategory(categoryId).map(category -> {
            categoryRepository.deleteById(categoryId);
            return true;
        }).orElse(false);
    }

    @Override
    public Page<Category> getAll(Pageable pageable, int page) {
        pageable = PageRequest.of(page, SIZE_DEFAULT);
        if (page >= categoryRepository.findAll(pageable).getTotalPages()) {
            throw new NotFoundException("Cannot find more pages");
        }
        return categoryRepository.findAll(pageable);
    }
}
