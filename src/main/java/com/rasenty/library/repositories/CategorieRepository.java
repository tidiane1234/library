package com.rasenty.library.repositories;

import com.rasenty.library.entities.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepository extends JpaRepository<Categorie , Long>{
    boolean existsByNom(String nom);
}
