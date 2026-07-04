package com.budget.management.service;

import com.budget.management.dto.BudgetDTO;
import com.budget.management.entity.Budget;
import com.budget.management.entity.Category;
import com.budget.management.repository.BudgetRepository;
import com.budget.management.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;

    public BudgetService(BudgetRepository budgetRepository, CategoryRepository categoryRepository) {
        this.budgetRepository = budgetRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<BudgetDTO> getAllBudgets() {
        return budgetRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public Optional<BudgetDTO> getBudgetById(Long id) {
        return budgetRepository.findById(id)
                .map(this::convertToDTO);
    }

    public List<BudgetDTO> getBudgetsByPeriod(YearMonth period) {
        return budgetRepository.findByPeriod(period)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<BudgetDTO> getBudgetsByCategory(Long categoryId) {
        // Remplacez par le nom de méthode exact de votre BudgetRepository si nécessaire (ex: findByCategorieBudget_Id)
        return budgetRepository.findByCategoryId(categoryId)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public BudgetDTO createBudget(BudgetDTO budgetDTO) {
        Budget budget = convertToEntity(budgetDTO);
        Budget savedBudget = budgetRepository.save(budget);
        return convertToDTO(savedBudget);
    }

    public BudgetDTO updateBudget(Long id, BudgetDTO budgetDetailsDTO) {
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Budget not found"));

        budget.setName(budgetDetailsDTO.getName());
        budget.setLimitAmount(budgetDetailsDTO.getLimitAmount());
        budget.setPeriod(budgetDetailsDTO.getPeriod());

        if (budgetDetailsDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(budgetDetailsDTO.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            budget.setCategorie_budget(category);
        }

        Budget updatedBudget = budgetRepository.save(budget);
        return convertToDTO(updatedBudget);
    }

    public void deleteBudget(Long id) {
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Budget not found"));
        budgetRepository.delete(budget);
    }

    // --- Méthodes utilitaires de conversion (Mapping) ---
    private BudgetDTO convertToDTO(Budget budget) {
        BudgetDTO dto = new BudgetDTO();
        dto.setId(budget.getId());
        dto.setName(budget.getName());
        dto.setLimitAmount(budget.getLimitAmount());
        dto.setPeriod(budget.getPeriod());

        if (budget.getCategorie_budget() != null) {
            dto.setCategoryId(budget.getCategorie_budget().getId());
            dto.setCategoryName(budget.getCategorie_budget().getName()); // Pratique pour l'affichage Angular
        }
        return dto;
    }

    private Budget convertToEntity(BudgetDTO dto) {
        Budget budget = new Budget();
        budget.setId(dto.getId());
        budget.setName(dto.getName());
        budget.setLimitAmount(dto.getLimitAmount());
        budget.setPeriod(dto.getPeriod());

        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            budget.setCategorie_budget(category);
        }
        return budget;
    }
}


//package com.budget.management.service;
//
//import com.budget.management.entity.Budget;
//import com.budget.management.entity.Category;
//import com.budget.management.repository.BudgetRepository;
//import com.budget.management.repository.CategoryRepository;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.YearMonth;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@Transactional
//public class BudgetService {
//
//    private final BudgetRepository budgetRepository;
//    private final CategoryRepository categoryRepository;
//
//    public BudgetService(BudgetRepository budgetRepository, CategoryRepository categoryRepository) {
//        this.budgetRepository = budgetRepository;
//        this.categoryRepository = categoryRepository;
//    }
//
//    public List<Budget> getAllBudgets() {
//        return budgetRepository.findAll();
//    }
//
//    public Optional<Budget> getBudgetById(Long id) {
//        return budgetRepository.findById(id);
//    }
//
//    public List<Budget> getBudgetsByPeriod(YearMonth period) {
//        return budgetRepository.findByPeriod(period);
//    }
//
//    public List<Budget> getBudgetsByCategory(Long categoryId) {
//        return budgetRepository.findByCategoryId(categoryId);
//    }
//
//    public Budget createBudget(Budget budget) {
//        if (budget.getCategory() != null && budget.getCategory().getId() != null) {
//            Category category = categoryRepository.findById(budget.getCategory().getId())
//                    .orElseThrow(() -> new RuntimeException("Category not found"));
//            budget.setCategory(category);
//        }
//        return budgetRepository.save(budget);
//    }
//
//    public Budget updateBudget(Long id, Budget budgetDetails) {
//        Budget budget = budgetRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Budget not found"));
//
//        budget.setName(budgetDetails.getName());
//        budget.setLimitAmount(budgetDetails.getLimitAmount());
//        budget.setPeriod(budgetDetails.getPeriod());
//
//        if (budgetDetails.getCategory() != null && budgetDetails.getCategory().getId() != null) {
//            Category category = categoryRepository.findById(budgetDetails.getCategory().getId())
//                    .orElseThrow(() -> new RuntimeException("Category not found"));
//            budget.setCategory(category);
//        }
//
//        return budgetRepository.save(budget);
//    }
//
//    public void deleteBudget(Long id) {
//        Budget budget = budgetRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Budget not found"));
//        budgetRepository.delete(budget);
//    }
//}
