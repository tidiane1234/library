package com.rasenty.library.controllers;

import com.rasenty.library.entities.Book;
import com.rasenty.library.exchange.BookCreationRequest;
import com.rasenty.library.exchange.BookResponseRequest;
import com.rasenty.library.services.BookService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookResponseRequest> getAll(){
        return bookService.getAll();
    }

    @GetMapping("/{id}")
    public BookResponseRequest findById(@PathVariable Long id){
        return bookService.findById(id);
    }

    @PostMapping("/create")
    public Book add(@RequestBody @Valid BookCreationRequest request){
        return bookService.add(request);
    }

    @PutMapping("/update/{id}")
    public Book update(@PathVariable Long id,@RequestBody @Valid Book bookUpdate){
       return bookService.update(id, bookUpdate);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
