package com.fvalle.company.service.impl;

import com.fvalle.company.dto.CategoryDto;
import com.fvalle.company.entity.Category;
import com.fvalle.company.entity.Employee;
import com.fvalle.company.exception.BadRequestException;
import com.fvalle.company.exception.NotFoundException;
import com.fvalle.company.mapper.CategoryMapper;
import com.fvalle.company.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category;
    private Category categoryTwo;
    private CategoryDto categoryDto;

    private List<Category> categories;

    @BeforeEach
    void setUp() {

        category = new Category();
        category.setId(1);
        category.setDescription("Sports");
        category.setState(true);

        categoryTwo = new Category();
        categoryTwo.setId(2);
        categoryTwo.setDescription("Technology");
        categoryTwo.setState(true);

        categories = new ArrayList<>();
        categories.add(category);
        categories.add(categoryTwo);

        categoryDto = new CategoryDto();
        categoryDto.setCategoryId(1);
        categoryDto.setCategory("Sports");
        categoryDto.setActive(true);
    }

    @Test
    void getAllCategoryDto() {
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category));
        Assertions.assertNotNull(categoryService.getAll());
    }

    @Test
    void getCategoryById() {
        Optional<Category> optionalEntity = Optional.of(category);

        when(categoryRepository.findById(1)).thenReturn(optionalEntity);
        Category categoryEntity = optionalEntity.get();

        when(categoryService.getCategory(1)).thenReturn(categoryDto);
        CategoryDto result = categoryService.getCategory(1);

        assertEquals(categoryMapper.toCategoryDto(categoryEntity), result);
    }

    @Test
    void getCategoryByIdNotFound() {
        when(categoryRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> categoryService.getCategory(1));
        verify(categoryRepository).findById(1);
    }

    @Test
    void saveCategoryOK() {
        CategoryDto simulated = new CategoryDto();
        simulated.setCategory("Technology");
        simulated.setActive(true);

        when(categoryService.save(categoryDto)).thenReturn(categoryDto);

        CategoryDto result = categoryService.save(categoryDto);

        assertEquals(categoryDto.getCategoryId(),result.getCategoryId());
        assertEquals(categoryDto.getCategory(),result.getCategory());
        assertEquals(categoryDto.isActive(),result.isActive());

    }

    @Test
    void saveCategoryErrorWithCategoryFieldNullOrEmpty() {
        CategoryDto simulated = new CategoryDto();
        simulated.setCategory("");
        simulated.setActive(true);

        assertThrows(BadRequestException.class, () -> {
            categoryService.save(simulated);
        });

    }

    @Test
    void delete() {
        Optional<Category> optionalEntity = Optional.of(category);

        Mockito.when(categoryRepository.findById(1)).thenReturn(optionalEntity);
        Category categoryEntity = optionalEntity.get();

        categoryService.delete(categoryEntity.getId());

        assertTrue(categoryService.delete(categoryEntity.getId()));
    }

    @Test
    void getAll() {
        int page = 0;
        int pageSize = 10;

        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Category> categoryPage = new PageImpl<>(categories, pageable, categories.size());

        when(categoryRepository.findAll(pageable)).thenReturn(categoryPage);

        Page<Category> result = categoryService.getAll(pageable, page);

        assertEquals(categoryPage, result);
    }

    @Test
    void testGetAll_ThrowsNotFoundException() {
        int page = 1;
        int pageSize = 10;

        Pageable pageable = PageRequest.of(page, pageSize);
        Pageable pageable1 = PageRequest.of(0, pageSize);
        Page<Category> categoryPage = new PageImpl<>(categories, pageable1, categories.size());

        when(categoryRepository.findAll(pageable)).thenReturn(categoryPage);

        assertThrows(NotFoundException.class, () -> categoryService.getAll(pageable, page));
    }
}