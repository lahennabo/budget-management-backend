package com.budget.management.controller;

import com.budget.management.dto.BudgetDTO;
import com.budget.management.service.BudgetService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.time.YearMonth;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/budgets")
@CrossOrigin(origins = "http://localhost:4200")
public class BudgetController {

    private final BudgetService budgetService;

    // Suppression de CategoryService ici : c'est la couche Service qui gère les dépendances métier
    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @GetMapping
    public ResponseEntity<List<BudgetDTO>> getAllBudgets() {
        List<BudgetDTO> budgets = budgetService.getAllBudgets();
        return ResponseEntity.ok(budgets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BudgetDTO> getBudgetById(@PathVariable Long id) {
        return budgetService.getBudgetById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/period/{period}")
    public ResponseEntity<List<BudgetDTO>> getBudgetsByPeriod(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM") YearMonth period) {
        List<BudgetDTO> budgets = budgetService.getBudgetsByPeriod(period);
        return ResponseEntity.ok(budgets);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<BudgetDTO>> getBudgetsByCategory(@PathVariable Long categoryId) {
        List<BudgetDTO> budgets = budgetService.getBudgetsByCategory(categoryId);
        return ResponseEntity.ok(budgets);
    }
    /*
    POST : http://localhost:8080/api/budgets
    {
      "name": "Budget Informatique",
      "limitAmount": 1500.50,
      "period": "2026-06",
      "categoryId": 4
    }
    */
    @PostMapping
    public ResponseEntity<BudgetDTO> createBudget(@RequestBody BudgetDTO budgetDTO) { //@Valid
        log.info(">>> budgetDTO : {}", budgetDTO);
        BudgetDTO createdBudget = budgetService.createBudget(budgetDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBudget);
    }
    /*
    PUT : http://localhost:8080/api/budgets/2
    {
      "name": "Budget Informatique avec cat:3",
      "limitAmount": 1500.50,
      "period": "2026-06",
      "categoryId": 3
    }
    */
    @PutMapping("/{id}")
    public ResponseEntity<BudgetDTO> updateBudget(@PathVariable Long id, @Valid @RequestBody BudgetDTO budgetDTO) {
        try {
            BudgetDTO updatedBudget = budgetService.updateBudget(id, budgetDTO);
            return ResponseEntity.ok(updatedBudget);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudget(@PathVariable Long id) {
        try {
            budgetService.deleteBudget(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}