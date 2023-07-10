package com.fvalle.company.repository;

import com.fvalle.company.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
    List<Category> findAllByStateTrueOrderByDescriptionDesc();
    Category findByDescriptionIgnoreCase(String description);
}
