package com.fvalle.company.service;

import com.fvalle.company.dto.CategoryDto;
import com.fvalle.company.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {

    List<CategoryDto> getAll();
    CategoryDto getCategory(int categoryId);
    CategoryDto save(CategoryDto categoryDto);
    public boolean delete(int categoryId);
    Page<Category> getAll(Pageable pageable, int page);
}
