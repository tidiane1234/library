package com.rasenty.library.controllers;

import com.rasenty.library.entities.Categorie;
import com.rasenty.library.exchange.CategorieCreationRequest;
import com.rasenty.library.exchange.CategorieResponseRequest;
import com.rasenty.library.services.CategorieService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategorieController {
    private final CategorieService categorieService;

    public CategorieController(CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    @GetMapping
    public List<CategorieResponseRequest> getAll(){
        return categorieService.getAll();
    }

    @GetMapping("/{id}")
    public CategorieResponseRequest findById(@PathVariable Long id){
        return categorieService.findById(id);
    }

    @PostMapping("/create")
    public Categorie add(@RequestBody @Valid CategorieCreationRequest request){
        return categorieService.add(request);
    }

    @PutMapping("/update/{id}")
    public Categorie update(@PathVariable Long id,@Valid @RequestBody Categorie categorieUpdate){
       return categorieService.update(id,categorieUpdate);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        categorieService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
