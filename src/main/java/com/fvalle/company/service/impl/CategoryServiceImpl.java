package com.fvalle.company.service.impl;

import com.fvalle.company.dto.CategoryDto;
import com.fvalle.company.entity.Category;
import com.fvalle.company.exception.BadRequestException;
import com.fvalle.company.exception.ErrorDetails;
import com.fvalle.company.exception.NotFoundException;
import com.fvalle.company.mapper.CategoryMapper;
import com.fvalle.company.repository.CategoryRepository;
import com.fvalle.company.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.fvalle.company.utils.CheckNullField.*;

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

    public List<Category> getAllCategoriesByStateTrue() {
        List<Category> categoryList = categoryRepository.findAllByStateTrueOrderByDescriptionDesc();
        return categoryList;
    }

    public CategoryDto findByDescriptionIgnoreCase(String description){
        /*if(description.contains("0123456789")){
            throw new NotFoundException("Cannot find categoryDto because the entered " +
                                        "value cannot contain numbers");
        }*/
        Category category = categoryRepository.findByDescriptionIgnoreCase(description);
        return categoryMapper.toCategoryDto(category);
    }

    @Override
    public CategoryDto getCategory(int id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Id " + id + " cannot be found"));
        return categoryMapper.toCategoryDto(category);
    }

    @Override
    @Transactional
    public CategoryDto save(CategoryDto categoryDto) {
        String validCategory = "^[A-Z]'?[- a-zA-Z]*$";

        if(checkError(c -> c.getCategory() == null || !isValid(c.getCategory(),validCategory), categoryDto)){
            List<ErrorDetails> list = new ArrayList<>();
            checkIfIsNull(categoryDto.getCategory(),"Category",n -> n == null,validCategory, list);
            throw new BadRequestException("GSS-400-003",HttpStatus.BAD_REQUEST,"All fields must be send",list);
        }

        Category category = categoryMapper.toCategory(categoryDto);
        return categoryMapper.toCategoryDto(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public boolean delete(int categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        //Category category = categoryMapper.toCategory(getCategory(categoryId));
        if(category.isPresent()){
            categoryRepository.deleteById(categoryId);
            return true;
        }else{
            return false;
        }
        /*return getCategory(categoryId).map(category -> {
            categoryRepository.deleteById(categoryId);
            return true;
        }).orElse(false);*/
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
