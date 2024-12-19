package com.rasenty.library.exchange;

import com.rasenty.library.entities.Book;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CategorieCreationRequest {
    @NotBlank(message = "le champ nom est obligatoire")
    @Size(min=2,max = 15)
    private String nom;
}
