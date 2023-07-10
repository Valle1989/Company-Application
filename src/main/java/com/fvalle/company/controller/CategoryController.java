package com.fvalle.company.controller;

import com.fvalle.company.dto.CategoryDto;
import com.fvalle.company.entity.Category;
import com.fvalle.company.service.ICategoryService;
import com.fvalle.company.service.impl.CategoryServiceImpl;
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
    private final CategoryServiceImpl categoryServiceImpl;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<CategoryDto> addCategory(@Valid @RequestBody CategoryDto categoryDto){
        return new ResponseEntity<>(categoryService.save(categoryDto), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('user:read', 'admin:read')")
    public ResponseEntity<List<CategoryDto>> getAll(){
        return new ResponseEntity<>(categoryService.getAll(),HttpStatus.OK);
    }

    @GetMapping("/all/state")
    @PreAuthorize("hasAnyAuthority('user:read', 'admin:read')")
    public ResponseEntity<List<Category>> getAllCategoriesByStateTrue(){
        return new ResponseEntity<>(categoryServiceImpl.getAllCategoriesByStateTrue(),HttpStatus.OK);
    }

    @GetMapping("/byDescription")
    @PreAuthorize("hasAnyAuthority('user:read', 'admin:read')")
    public ResponseEntity<CategoryDto> findByDescriptionIgnoreCase(@RequestParam("description") String description){
        return new ResponseEntity<>(categoryServiceImpl.findByDescriptionIgnoreCase(description),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('user:read', 'admin:read')")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("id") int id){
        return new ResponseEntity<>(categoryService.getCategory(id), HttpStatus.OK);
    }

    @GetMapping("/page")
    @PreAuthorize("hasAnyAuthority('user:read', 'admin:read')")
    public Page<Category> getAllCategories(Pageable pageable, int page) {
        Page<Category> result = categoryService.getAll(pageable,page);
        return result;
    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<String> delete(@Valid @PathVariable("id") int categoryId) {
        if (categoryService.delete(categoryId)) {
            return new ResponseEntity<>("Category " + categoryId + " deleted",HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Category " + categoryId + " not found",HttpStatus.NOT_FOUND);
        }
    }
}
