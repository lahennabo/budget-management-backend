package com.budget.management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.YearMonth;

@Data
@Entity
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal limitAmount;

    @NotNull
    @Column(nullable = false)
    private YearMonth period;

    @ManyToOne
    @JoinColumn(name = "categorie_id", nullable = false)
    private Category categorie_budget;

}