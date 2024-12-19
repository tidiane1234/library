package com.rasenty.library.services;

import com.rasenty.library.entities.Book;
import com.rasenty.library.entities.Categorie;
import com.rasenty.library.exceptions.CategorieNotEmptyException;
import com.rasenty.library.exceptions.InvalidInputException;
import com.rasenty.library.exchange.CategorieCreationRequest;
import com.rasenty.library.exchange.CategorieResponseRequest;
import com.rasenty.library.repositories.CategorieRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategorieService {


    private final CategorieRepository categorieRepository;

    public CategorieService(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    public Categorie add(CategorieCreationRequest request){
        if (categorieRepository.existsByNom(request.getNom())) {
            throw new InvalidInputException("Une catégorie avec ce nom existe déjà.");
        }
        Categorie categorie = Categorie.builder().nom(request.getNom()).build();
        return categorieRepository.save(categorie);
    }

    public Categorie searchById(Long id){
        return categorieRepository.findById(id).orElseThrow(
                ()->new RuntimeException("cette categorie n'existe pas ")
        );
    }

    public Categorie searchByNom(String name){
        return categorieRepository.findByNom(name).orElseThrow(
                ()->new RuntimeException("cette categorie n'existe pas ")
        );
    }

    public List<CategorieResponseRequest> getAll(){
        return categorieRepository.findAll()
                .stream()
                .map(categorie -> new CategorieResponseRequest(
                        categorie.getId(),
                        categorie.getNom(),
                        categorie.getBooks() != null
                                ? categorie.getBooks()
                                .stream()
                                .map(book -> {
                                    return book.getTitre();
                                }).collect(Collectors.toList())
                                :null
                ))
                .collect(Collectors.toList());
    }

    public CategorieResponseRequest findById(Long id){
        Categorie categorie = categorieRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("la categorie n'existe pas "));
        return new CategorieResponseRequest(
                categorie.getId(),
                categorie.getNom(),
                categorie.getBooks()
                        .stream()
                        .map(book -> {
                            return book.getTitre();
                        })
                        .collect(Collectors.toList())
        );
    }

    public void delete(Long id){
        Categorie categorie = categorieRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Catégorie non trouvée."));
        if (!categorie.getBooks().isEmpty()) {
            throw new CategorieNotEmptyException("Impossible de supprimer une catégorie contenant des livres.");
        }
        categorieRepository.delete(categorie);
    }

    public Categorie update(Long id,Categorie categorieUpdate){
               return categorieRepository.findById(id)
                .map(categorie1 -> {
                    categorie1.setNom(categorieUpdate.getNom());
                    return categorieRepository.save(categorie1);
                })
                .orElseThrow(()-> new EntityNotFoundException("veuillez selectionner une autre qui existe "));
    }
}
