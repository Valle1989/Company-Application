package com.fvalle.company.controller;

import com.fvalle.company.dto.CategoryDto;
import com.fvalle.company.entity.Category;
import com.fvalle.company.service.ICategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@AllArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class CategoryController {

    private final ICategoryService categoryService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<CategoryDto> addCategory(@Valid @RequestBody CategoryDto categoryDto){
        return new ResponseEntity<>(categoryService.save(categoryDto), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<List<CategoryDto>> getAll(){
        return new ResponseEntity<>(categoryService.getAll(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("id") int categoryId){
        return categoryService.getCategory(categoryId)
                .map(category -> new ResponseEntity<>(category, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/page")
    @PreAuthorize("hasAuthority('admin:read')")
    public Page<Category> getAllCategories(Pageable pageable, int page) {
        Page<Category> result = categoryService.getAll(pageable,page);
        return result;
    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<?> delete(@Valid @PathVariable("id") int categoryId) {
        if (categoryService.delete(categoryId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
