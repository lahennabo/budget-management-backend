package com.budget.management.controller;

import com.budget.management.dto.TransactionDTO;
import com.budget.management.entity.TransactionType;
import com.budget.management.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "http://localhost:4200")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable Long id) {
        return transactionService.getTransactionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(transactionService.getTransactionsByCategory(categoryId));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByType(@PathVariable TransactionType type) {
        return ResponseEntity.ok(transactionService.getTransactionsByType(type));
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(transactionService.getTransactionsByDateRange(startDate, endDate));
    }

    @GetMapping("/summary/category/{categoryId}")
    public ResponseEntity<BigDecimal> getTotalByTypeAndCategory(
            @PathVariable Long categoryId,
            @RequestParam TransactionType type) {
        return ResponseEntity.ok(transactionService.getTotalByTypeAndCategory(type, categoryId));
    }
    /*
    GET : http://localhost:8080/api/transactions
                /summary/date-range?type=DÉPENSES&startDate=2026-06-01&endDate=2026-06-30
    */
    @GetMapping("/summary/date-range")
    public ResponseEntity<BigDecimal> getTotalByTypeAndDateRange(
            @RequestParam TransactionType type,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(transactionService.getTotalByTypeAndDateRange(type, startDate, endDate));
    }
    /*
    POST : http://localhost:8080/api/transactions
    {
        "description": "Achat de provisions supermarché",
            "amount": 75.50,
            "type": "DÉPENSES",
            "date": "2026-06-29",
            "categoryId": 6
    }
    */
    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
        TransactionDTO created = transactionService.createTransaction(transactionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    /*
    PUT : http://localhost:8080/api/transactions/1
    {
        "description": "Achat de provisions supermarché avec cat : 7",
            "amount": 75.50,
            "type": "DÉPENSES",
            "date": "2026-06-29",
            "categoryId": 7
    }
    */
    @PutMapping("/{id}")
    public ResponseEntity<TransactionDTO> updateTransaction(@PathVariable Long id, @Valid @RequestBody TransactionDTO transactionDTO) {
        try {
            return ResponseEntity.ok(transactionService.updateTransaction(id, transactionDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        try {
            transactionService.deleteTransaction(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}



//
//import com.budget.management.entity.Transaction;
//import com.budget.management.entity.TransactionType;
//import com.budget.management.service.TransactionService;
//import jakarta.validation.Valid;
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/transactions")
//@CrossOrigin(origins = "http://localhost:4200")
//public class TransactionController {
//
//    private final TransactionService transactionService;
//
//    public TransactionController(TransactionService transactionService) {
//        this.transactionService = transactionService;
//    }
//
//    @GetMapping
//    public ResponseEntity<List<Transaction>> getAllTransactions() {
//        List<Transaction> transactions = transactionService.getAllTransactions();
//        return ResponseEntity.ok(transactions);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
//        return transactionService.getTransactionById(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    @GetMapping("/category/{categoryId}")
//    public ResponseEntity<List<Transaction>> getTransactionsByCategory(@PathVariable Long categoryId) {
//        List<Transaction> transactions = transactionService.getTransactionsByCategory(categoryId);
//        return ResponseEntity.ok(transactions);
//    }
//
//    @GetMapping("/type/{type}")
//    public ResponseEntity<List<Transaction>> getTransactionsByType(@PathVariable TransactionType type) {
//        List<Transaction> transactions = transactionService.getTransactionsByType(type);
//        return ResponseEntity.ok(transactions);
//    }
//
//    @GetMapping("/date-range")
//    public ResponseEntity<List<Transaction>> getTransactionsByDateRange(
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
//        List<Transaction> transactions = transactionService.getTransactionsByDateRange(startDate, endDate);
//        return ResponseEntity.ok(transactions);
//    }
//
//    @GetMapping("/summary/category/{categoryId}")
//    public ResponseEntity<BigDecimal> getTotalByTypeAndCategory(
//            @PathVariable Long categoryId,
//            @RequestParam TransactionType type) {
//        BigDecimal total = transactionService.getTotalByTypeAndCategory(type, categoryId);
//        return ResponseEntity.ok(total);
//    }
//
//    @GetMapping("/summary/date-range")
//    public ResponseEntity<BigDecimal> getTotalByTypeAndDateRange(
//            @RequestParam TransactionType type,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
//        BigDecimal total = transactionService.getTotalByTypeAndDateRange(type, startDate, endDate);
//        return ResponseEntity.ok(total);
//    }
//
//    @PostMapping
//    public ResponseEntity<Transaction> createTransaction(@Valid @RequestBody Transaction transaction) {
//        Transaction createdTransaction = transactionService.createTransaction(transaction);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdTransaction);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long id, @Valid @RequestBody Transaction transaction) {
//        try {
//            Transaction updatedTransaction = transactionService.updateTransaction(id, transaction);
//            return ResponseEntity.ok(updatedTransaction);
//        } catch (RuntimeException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
//        try {
//            transactionService.deleteTransaction(id);
//            return ResponseEntity.noContent().build();
//        } catch (RuntimeException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
//}
