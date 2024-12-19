package com.rasenty.library.repositories;

import com.rasenty.library.entities.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategorieRepository extends JpaRepository<Categorie , Long>{
    boolean existsByNom(String nom);

    Optional<Categorie> findByNom(String name);
}
