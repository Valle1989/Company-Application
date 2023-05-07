package com.fvalle.company.mapper;

import com.fvalle.company.dto.CategoryDto;
import com.fvalle.company.entity.Category;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mappings({
            @Mapping(source = "id", target = "categoryId"),
            @Mapping(source = "description", target = "category"),
            @Mapping(source = "state", target = "active"),
    })
    CategoryDto toCategoryDto(Category category);
    List<CategoryDto> toCategories(List<Category> categoryList);

    @InheritInverseConfiguration
    @Mapping(target = "products", ignore = true)
    Category toCategory(CategoryDto categoryDto);
}
