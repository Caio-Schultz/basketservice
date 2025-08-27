package dev.java.ecommerce.basketservice.controller;

import dev.java.ecommerce.basketservice.controller.request.BasketRequest;
import dev.java.ecommerce.basketservice.entity.Basket;
import dev.java.ecommerce.basketservice.service.BasketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/basket")
public class BasketController {

    private final BasketService service;

    public BasketController(BasketService service) {
        this.service = service;
    }


    @PostMapping
    public ResponseEntity<Basket> create(@RequestBody BasketRequest request){


        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBasketById(@PathVariable String id){
        Basket basket = service.getBasketById(id);

        if (basket != null){
            return ResponseEntity.ok(basket);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Carrinho não encontrado!");
        }

    }

}
