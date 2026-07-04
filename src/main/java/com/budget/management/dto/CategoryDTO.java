package com.budget.management.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryDTO {
    private Long id;

    @NotBlank(message = "Le nom de la catégorie est obligatoire")
    private String name;

    private String description;

    @NotBlank(message = "La couleur est obligatoire")
    private String color;
}
