package com.rasenty.library.services;

import com.rasenty.library.entities.Book;
import com.rasenty.library.entities.Categorie;
import com.rasenty.library.exceptions.EntityNotFoundException;
import com.rasenty.library.exceptions.InvalidInputException;
import com.rasenty.library.exchange.BookCreationRequest;
import com.rasenty.library.exchange.BookResponseRequest;
import com.rasenty.library.repositories.BookRepository;
import com.rasenty.library.repositories.CategorieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final CategorieService categorieService;

    public BookService(BookRepository bookRepository, CategorieService categorieService) {
        this.bookRepository = bookRepository;
        this.categorieService = categorieService;
    }

    public Book add(BookCreationRequest request){
        Categorie categorie =categorieService.searchById(request.getCategorieId());
        if (bookRepository.existsByIsbn(request.getIsbn())) {
            throw new InvalidInputException("Un livre avec cet ISBN existe déjà.");
        }
        Book book = Book.builder()
               .titre(request.getTitre())
               .auteur(request.getAuteur())
               .isbn(request.getIsbn())
               .datePublication(request.getDatePublication())
               .categorie(categorie)
               .build();

       return bookRepository.save(book);
    }



    public BookResponseRequest findById(Long id){
        Book book = bookRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("le livre n'existe pas "));
        return new BookResponseRequest(
                book.getId(),
                book.getTitre(),
                book.getAuteur(),
                book.getDatePublication(),
                book.getCategorie() != null ? book.getCategorie().getNom() : null
        );
    }

    public List<BookResponseRequest> getAll(){
        return bookRepository.findAll()
                .stream()
                .map(book -> new BookResponseRequest(
                        book.getId(),
                        book.getTitre(),
                        book.getAuteur(),
                        book.getDatePublication(),
                        book.getCategorie() != null ? book.getCategorie().getNom() : null
                ))
                .collect(Collectors.toList());
    }

    public void delete(Long id){
        bookRepository.deleteById(id);
    }

    public Book update(Long id, Book bookUpdate){
        return bookRepository.findById(id).map(book1 -> {
            book1.setTitre(bookUpdate.getTitre());
            book1.setAuteur(bookUpdate.getAuteur());
            book1.setIsbn(bookUpdate.getIsbn());
            book1.setDatePublication(bookUpdate.getDatePublication());
            return bookRepository.save(book1);
        }).orElseThrow(()-> new EntityNotFoundException("livre introuvable ,veuillez selectionner le bon Id "));
    }

}
