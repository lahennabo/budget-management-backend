package com.budget.management.controller;

import com.budget.management.dto.CategoryDTO;
import com.budget.management.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:4200")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categoryDTOS = categoryService.getAllCategories();
        return ResponseEntity.ok(categoryDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id) // Le service doit mapper l'entité en DTO ici
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    /*
    POST : http://localhost:8080/api/categories
    {
        "name": "Transport",
        "description": "Carburant, transports en commun et entretien voiture",
        "color": "#9467BD"
    }
     */
    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO createdCategory = categoryService.createCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }
    /*
    PUT : http://localhost:8080/api/categories/3   // id=3
    {
        "name": "Transport",
        "description": "Carburant, transports en commun et entretien voiture",
        "color": "#9467BD"
    }
     */
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDTO categoryDTO) {
        try {
            CategoryDTO updatedCategory = categoryService.updateCategory(id, categoryDTO);
            return ResponseEntity.ok(updatedCategory);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
