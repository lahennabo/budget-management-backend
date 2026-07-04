package com.budget.management.dto;

import com.budget.management.entity.TransactionType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionDTO {
    private Long id;

    @NotBlank(message = "La description est obligatoire")
    private String description;

    @NotNull(message = "Le montant est obligatoire")
    private BigDecimal amount;

    @NotNull(message = "Le type de transaction est obligatoire")
    private TransactionType type;

    @NotNull(message = "La date est obligatoire")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotNull(message = "L'ID de la catégorie est obligatoire")
    private Long categoryId;

    private String categoryName;
}
