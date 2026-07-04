package com.budget.management.service;

import com.budget.management.dto.CategoryDTO;
import com.budget.management.entity.Category;
import com.budget.management.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<CategoryDTO> getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(this::convertToDTO);
    }

    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        if (categoryRepository.existsByName(categoryDTO.getName())) {
            throw new RuntimeException("Category with name '" + categoryDTO.getName() + "' already exists");
        }

        Category category = convertToEntity(categoryDTO);
        Category savedCategory = categoryRepository.save(category);

        return convertToDTO(savedCategory);
    }

    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDetailsDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setName(categoryDetailsDTO.getName());
        category.setDescription(categoryDetailsDTO.getDescription());
        category.setColor(categoryDetailsDTO.getColor());

        Category updatedCategory = categoryRepository.save(category);
        return convertToDTO(updatedCategory);
    }

    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        categoryRepository.delete(category);
    }

    // --- Méthodes utilitaires de conversion (Mapping) ---

    private CategoryDTO convertToDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        dto.setColor(category.getColor());
        return dto;
    }

    private Category convertToEntity(CategoryDTO dto) {
        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setColor(dto.getColor());
        return category;
    }
}


//package com.budget.management.service;
//
//import com.budget.management.entity.Category;
//import com.budget.management.repository.CategoryRepository;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//@Transactional
//public class CategoryService {
//
//    private final CategoryRepository categoryRepository;
//
//    public CategoryService(CategoryRepository categoryRepository) {
//        this.categoryRepository = categoryRepository;
//    }
//
//    public List<CategoryRecordDTO> getAllCategories() {
//        return categoryRepository.findAll()
//                .stream()
//                .map(cat -> new CategoryRecordDTO(cat.getId(), cat.getName(), cat.getDescription(), cat.getColor()))
//                .collect(Collectors.toList());
//    }
//
//
////    public List<Category> getAllCategories() {
////        return categoryRepository.findAll();
////    }
//
//    public Optional<Category> getCategoryById(Long id) {
//        return categoryRepository.findById(id);
//    }
//
//    public Category createCategory(Category category) {
//        if (categoryRepository.existsByName(category.getName())) {
//            throw new RuntimeException("Category with name '" + category.getName() + "' already exists");
//        }
//        return categoryRepository.save(category);
//    }
//
//    public Category updateCategory(Long id, Category categoryDetails) {
//        Category category = categoryRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Category not found"));
//
//        category.setName(categoryDetails.getName());
//        category.setDescription(categoryDetails.getDescription());
//        category.setColor(categoryDetails.getColor());
//
//        return categoryRepository.save(category);
//    }
//
//    public void deleteCategory(Long id) {
//        Category category = categoryRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Category not found"));
//        categoryRepository.delete(category);
//    }
//}
