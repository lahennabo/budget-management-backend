package com.budget.management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private String color;

    //mappedBy fait référence au champ "categorie" dans l'entité Budget
    @OneToMany(mappedBy = "categorie_budget", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Budget> budgets;

    // mappedBy évite la création d'une table intermédiaire inutile
    @OneToMany(mappedBy = "categorie_transaction", cascade = CascadeType.ALL)
    private List<Transaction> transactions;
}