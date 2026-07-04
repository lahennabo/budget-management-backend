package com.budget.management.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.time.YearMonth;

@Data
public class BudgetDTO {
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private BigDecimal limitAmount;

    // L'annotation cruciale pour éviter l'erreur 500 :
    @NotNull(message = "La période est obligatoire")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM")
    private YearMonth period;

    @NotNull
    private Long categoryId;

    private String categoryName;
}
