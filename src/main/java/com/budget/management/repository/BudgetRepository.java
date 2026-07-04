package com.budget.management.repository;

import com.budget.management.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.YearMonth;
import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    List<Budget> findByPeriod(YearMonth period);

    // En utilisant une méthode native (avec SQL), Spring Data désactive totalement
    // la validation automatique du nom de la méthode au démarrage.
    @Query(value = "SELECT * FROM budget WHERE categorie_id = :categoryId", nativeQuery = true)
    List<Budget> findByCategoryId(@Param("categoryId") Long categoryId);
}



/*
package com.budget.management.repository;

import com.budget.management.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    List<Budget> findByPeriod(YearMonth period);
    //List<Budget> findByCategorieBudget_Id(Long categoryId);

    @Query("SELECT b FROM Budget b WHERE b.categorie_budget.id = :categoryId")
    List<Budget> findByCategoryId(@Param("categoryId") Long categoryId);

    Optional<Budget> findByPeriodAndCategoryId(YearMonth period, Long categoryId);
}
*/