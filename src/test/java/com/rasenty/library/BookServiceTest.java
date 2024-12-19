package com.rasenty.library;

import com.rasenty.library.entities.Book;
import com.rasenty.library.exceptions.EntityNotFoundException;
import com.rasenty.library.exchange.BookCreationRequest;
import com.rasenty.library.exchange.BookResponseRequest;
import com.rasenty.library.services.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@Transactional
@Rollback
public class BookServiceTest {

    private final BookService bookService;
    @Autowired
    public BookServiceTest(BookService bookService) {
        this.bookService = bookService;
    }


    @Test
    void itShouldCreateBook(){
        BookCreationRequest bookCreationRequest = new BookCreationRequest(
                "java Spring","tidiane","12345664563608", LocalDate.now(),2L
        );

        Book book = bookService.add(bookCreationRequest);

        Assertions.assertNotNull(book.getId());
        Assertions.assertEquals(bookCreationRequest.getAuteur(), book.getAuteur());
    }

    @Test
    void itShouldGetAllBooks(){
        List<BookResponseRequest> books = bookService.getAll();

        Assertions.assertEquals(4,books.size());
        Assertions.assertEquals("java",books.get(3).getTitre());
    }

    @Test
    void itShouldFindBookById() {
        BookResponseRequest bookResponse = bookService.findById(3L);

        Assertions.assertEquals("camara laye", bookResponse.getAuteur());
        Assertions.assertEquals("l'enfant noir", bookResponse.getTitre());
    }

    @Test
    void itShouldUpdateBook(){
        BookResponseRequest bookResponse = bookService.findById(4L);

        Book updateBook = Book.builder()
                .titre("php")
                .auteur("bah")
                .isbn("765439")
                .datePublication(LocalDate.now())
                .build();

        Book result = bookService.update(bookResponse.getId(),updateBook);

        Assertions.assertEquals(result.getTitre(),updateBook.getTitre());
        Assertions.assertEquals(result.getIsbn(),updateBook.getIsbn());

    }

    @Test
    void itShouldDeleteBook(){
        BookResponseRequest bookResponse = bookService.findById(3L);

        bookService.delete(bookResponse.getId());
        Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> bookService.findById(bookResponse.getId()),
                "Le livre supprimé ne doit pas être retrouvé."
        );
    }

}
