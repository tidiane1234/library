package com.rasenty.library;

import com.rasenty.library.entities.Categorie;
import com.rasenty.library.exchange.CategorieCreationRequest;
import com.rasenty.library.exchange.CategorieResponseRequest;
import com.rasenty.library.services.CategorieService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CategorieServiceTest {

    private final CategorieService categorieService;


    @Autowired
    public CategorieServiceTest(CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    @Test
    void itShouldCreateCategorie(){
        CategorieCreationRequest categorieCreationRequest = CategorieCreationRequest.builder()
                .nom("litteraire")
                .build();
        Categorie categorie = categorieService.add(categorieCreationRequest);

        Assertions.assertNotNull(categorie.getId());
    }

    @Test
    void itShouldCategorieFindById(){
        CategorieResponseRequest categorieResponseRequest = categorieService.findById(1L);


    }
}
