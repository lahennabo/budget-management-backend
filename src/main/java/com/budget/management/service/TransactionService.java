package com.budget.management.service;

import com.budget.management.dto.TransactionDTO;
import com.budget.management.entity.Category;
import com.budget.management.entity.Transaction;
import com.budget.management.repository.CategoryRepository;
import com.budget.management.repository.TransactionRepository;
import com.budget.management.entity.TransactionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;

    public TransactionService(TransactionRepository transactionRepository, CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<TransactionDTO> getAllTransactions() {
        return transactionRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<TransactionDTO> getTransactionById(Long id) {
        return transactionRepository.findById(id).map(this::convertToDTO);
    }

    @Transactional(readOnly = true)
    public List<TransactionDTO> getTransactionsByCategory(Long categoryId) {
        return transactionRepository.findByCategorieTransactionId(categoryId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TransactionDTO> getTransactionsByType(TransactionType type) {
        return transactionRepository.findByType(type)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TransactionDTO> getTransactionsByDateRange(LocalDate startDate, LocalDate endDate) {
        return transactionRepository.findByDateBetween(startDate, endDate)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TransactionDTO createTransaction(TransactionDTO dto) {
        log.info(">>> Création de la transaction : {}", dto);
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Transaction transaction = convertToEntity(dto);
        transaction.setCategorie_transaction(category);

        return convertToDTO(transactionRepository.save(transaction));
    }

    public TransactionDTO updateTransaction(Long id, TransactionDTO dtoDetails) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        Category category = categoryRepository.findById(dtoDetails.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        transaction.setDescription(dtoDetails.getDescription());
        transaction.setAmount(dtoDetails.getAmount());
        transaction.setType(dtoDetails.getType());
        transaction.setDate(dtoDetails.getDate());
        transaction.setCategorie_transaction(category);

        return convertToDTO(transactionRepository.save(transaction));
    }

    public void deleteTransaction(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        transactionRepository.delete(transaction);
    }

    @Transactional(readOnly = true)
    public BigDecimal getTotalByTypeAndCategory(TransactionType type, Long categoryId) {
        BigDecimal sum = transactionRepository.sumByTypeAndCategory(type, categoryId);
        return sum != null ? sum : BigDecimal.ZERO;
    }

    @Transactional(readOnly = true)
    public BigDecimal getTotalByTypeAndDateRange(TransactionType type, LocalDate startDate, LocalDate endDate) {
        BigDecimal sum = transactionRepository.sumByTypeAndDateRange(type, startDate, endDate);
        return sum != null ? sum : BigDecimal.ZERO;
    }

    // --- MAPPING MANUAL ---
    private TransactionDTO convertToDTO(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setId(transaction.getId());
        dto.setDescription(transaction.getDescription());
        dto.setAmount(transaction.getAmount());
        dto.setType(transaction.getType());
        dto.setDate(transaction.getDate());
        if (transaction.getCategorie_transaction() != null) {
            dto.setCategoryId(transaction.getCategorie_transaction().getId());
            dto.setCategoryName(transaction.getCategorie_transaction().getName());
        }
        return dto;
    }

    private Transaction convertToEntity(TransactionDTO dto) {
        Transaction transaction = new Transaction();
        transaction.setId(dto.getId());
        transaction.setDescription(dto.getDescription());
        transaction.setAmount(dto.getAmount());
        transaction.setType(dto.getType());
        transaction.setDate(dto.getDate());
        return transaction;
    }
}



//package com.budget.management.service;
//
//import com.budget.management.dto.CategoryDTO;
//import com.budget.management.dto.TransactionDTO;
//import com.budget.management.entity.Category;
//import com.budget.management.entity.Transaction;
//import com.budget.management.repository.CategoryRepository;
//import com.budget.management.repository.TransactionRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Slf4j
//@Service
//@Transactional
//public class TransactionService {
//
//    private final TransactionRepository transactionRepository;
//    private final CategoryRepository categoryRepository;
//
//    public TransactionService(TransactionRepository transactionRepository, CategoryRepository categoryRepository) {
//        this.transactionRepository = transactionRepository;
//        this.categoryRepository = categoryRepository;
//    }
//    public List<TransactionDTO> getAllTransactions() {
//        return transactionRepository.findAll()
//                .stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//    }
//
//    private TransactionDTO convertToDTO(Transaction transaction) {
//        CategoryDTO categoryDTO = null;
//
//        if (transaction.getCategory() != null) {
//            categoryDTO = new CategoryDTO(
//                    transaction.getCategory().getId(),
//                    transaction.getCategory().getName(),
//                    transaction.getCategory().getDescription(),
//                    transaction.getCategory().getColor()
//            );
//        }
//        //String description, BigDecimal amount, String type, LocalDate date, Long categoryId, String notes
//        return new TransactionDTO(
//                transaction.getDescription(),
//                transaction.getAmount(),
//                transaction.getType(),
//                transaction.getDate(),
//                transaction.getCategory(),
//                transaction.getNotes()
//        );
//
////    public List<Transaction> getAllTransactions() {
////        return transactionRepository.findAll();
////    }
////
//    public Optional<Transaction> getTransactionById(Long id) {
//        return transactionRepository.findById(id);
//    }
//
//    public List<Transaction> getTransactionsByCategory(Long categoryId) {
//        return transactionRepository.findByCategoryId(categoryId);
//    }
//
//    public List<Transaction> getTransactionsByType(TransactionType type) {
//        return transactionRepository.findByType(type);
//    }
//
//    public List<Transaction> getTransactionsByDateRange(LocalDate startDate, LocalDate endDate) {
//        return transactionRepository.findByDateBetween(startDate, endDate);
//    }
//
//    public Transaction createTransaction(Transaction transaction) {
//        log.info(">>> transaction : {}", transaction);
//        Category category = categoryRepository.findById(transaction.getCategory().getId())
//                .orElseThrow(() -> new RuntimeException("Category not found"));
//        transaction.setCategory(category);
//        return transactionRepository.save(transaction);
//    }
//
//    public Transaction updateTransaction(Long id, Transaction transactionDetails) {
//        Transaction transaction = transactionRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Transaction not found"));
//
//        Category category = categoryRepository.findById(transactionDetails.getCategory().getId())
//                .orElseThrow(() -> new RuntimeException("Category not found"));
//
//        transaction.setDescription(transactionDetails.getDescription());
//        transaction.setAmount(transactionDetails.getAmount());
//        transaction.setType(transactionDetails.getType());
//        transaction.setDate(transactionDetails.getDate());
//        transaction.setCategory(category);
//        transaction.setNotes(transactionDetails.getNotes());
//
//        return transactionRepository.save(transaction);
//    }
//
//    public void deleteTransaction(Long id) {
//        Transaction transaction = transactionRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Transaction not found"));
//        transactionRepository.delete(transaction);
//    }
//
//    public BigDecimal getTotalByTypeAndCategory(TransactionType type, Long categoryId) {
//        BigDecimal sum = transactionRepository.sumByTypeAndCategory(type, categoryId);
//        return sum != null ? sum : BigDecimal.ZERO;
//    }
//
//    public BigDecimal getTotalByTypeAndDateRange(TransactionType type, LocalDate startDate, LocalDate endDate) {
//        BigDecimal sum = transactionRepository.sumByTypeAndDateRange(type, startDate, endDate);
//        return sum != null ? sum : BigDecimal.ZERO;
//    }
//}
