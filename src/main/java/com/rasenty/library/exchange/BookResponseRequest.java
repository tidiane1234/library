package com.rasenty.library.exchange;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookResponseRequest {
    private Long id;
    private String titre;
    private String auteur;
    private LocalDate datePublication;
    private String categorieName;
}
