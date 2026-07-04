package com.budget.management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String description;

    @NotNull
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @NotNull
    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "categorie_id", nullable = false)
    private Category categorie_transaction;
}


//package com.budget.management.entity;
//
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//import lombok.Data;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//
//@Data
//@Entity
//public class Transaction {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @NotBlank
//    @Column(nullable = false)
//    private String description;
//
//    @NotNull
//    @Column(nullable = false, precision = 10, scale = 2)
//    private BigDecimal amount;
//
//    @NotNull
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private TransactionType type;
//
//    @NotNull
//    @Column(nullable = false)
//    private LocalDate date;
//
//    // Crée une colonne "categorie_id" dans la table Transaction
//    @ManyToOne
//    @JoinColumn(name = "categorie_id", nullable = false)
//    private Category categorie_transaction;
//}
//
///*
// Conseil pour éviter les boucles infinies (JSON)
//
// Si vous exposez ces entités via une API REST
// (avec un @RestController), la relation bidirectionnelle peut provoquer une erreur de boucle
// infinie lors de la sérialisation en JSON.
// Pour éviter cela, utilisez ces annotations Jackson :Ajoutez @JsonManagedReference sur la liste
// transactions dans Categorie.Ajoutez @JsonBackReference sur le champ categorie dans Transaction.
// Souhaitez-vous des conseils pour gérer les performances lors du chargement des transactions
// (comme l'utilisation de FetchType.LAZY), ou préférez-vous voir comment créer un Repository
// pour filtrer les transactions par catégorie ?
// */