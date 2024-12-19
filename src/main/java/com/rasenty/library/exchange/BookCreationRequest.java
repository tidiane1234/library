package com.rasenty.library.exchange;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookCreationRequest {
    @NotBlank(message = "le champ titre est obligatoire")
    private String titre;
    @NotBlank(message = "le champ auteur est obligatoire")
    @Size(min =2,max =20)
    private String auteur;
    @NotBlank(message = "le champ isbn est obligatoire")
    private String isbn;
    private LocalDate datePublication;
    @NotNull(message = "Le champ categorieId est obligatoire")
    private Long categorieId;
}
