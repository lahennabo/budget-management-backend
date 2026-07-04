package com.budget.management.repository;

import com.budget.management.entity.Transaction;
import com.budget.management.entity.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Remplacez l'ancienne méthode findByCategorieTransactionId par celle-ci :
    @Query("SELECT t FROM Transaction t WHERE t.categorie_transaction.id = :categoryId")
    List<Transaction> findByCategorieTransactionId(@Param("categoryId") Long categoryId);

    //List<Transaction> findByCategorieTransactionId(Long categoryId);

    List<Transaction> findByType(TransactionType type);

    List<Transaction> findByDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.type = :type AND t.categorie_transaction.id = :categoryId")
    BigDecimal sumByTypeAndCategory(@Param("type") TransactionType type, @Param("categoryId") Long categoryId);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.type = :type AND t.date BETWEEN :startDate AND :endDate")
    BigDecimal sumByTypeAndDateRange(@Param("type") TransactionType type, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}


//package com.budget.management.repository;
//
//import com.budget.management.entity.Transaction;

//import com.budget.management.entity.TransactionType;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@Repository
//public interface TransactionRepository extends JpaRepository<Transaction, Long> {
//    List<Transaction> findByCategoryId(Long categoryId);
//    List<Transaction> findByType(TransactionType type);
//    List<Transaction> findByDateBetween(LocalDate startDate, LocalDate endDate);
//
//    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.type = :type AND t.category.id = :categoryId")
//    java.math.BigDecimal sumByTypeAndCategory(@Param("type") TransactionType type, @Param("categoryId") Long categoryId);
//
//    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.type = :type AND t.date BETWEEN :startDate AND :endDate")
//    java.math.BigDecimal sumByTypeAndDateRange(@Param("type") TransactionType type, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
//}
