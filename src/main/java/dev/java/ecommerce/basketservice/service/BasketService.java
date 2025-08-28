package dev.java.ecommerce.basketservice.service;

import dev.java.ecommerce.basketservice.client.response.PlatziProductResponse;
import dev.java.ecommerce.basketservice.controller.request.BasketRequest;
import dev.java.ecommerce.basketservice.controller.request.PaymentRequest;
import dev.java.ecommerce.basketservice.entity.Basket;
import dev.java.ecommerce.basketservice.entity.Product;
import dev.java.ecommerce.basketservice.enums.Status;
import dev.java.ecommerce.basketservice.repository.BasketRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BasketService {

    private final BasketRepository repository;
    private final ProductService productService;

    public BasketService(BasketRepository repository, ProductService productService) {
        this.repository = repository;
        this.productService = productService;
    }

    public Basket create (BasketRequest request){

        repository.findByClientIdAndStatus(request.clientId(), Status.OPEN)
                .ifPresent(basket -> {
                    throw new IllegalArgumentException("Já tenho um carrinho aberto para esse cliente!");
                });

        List<Product> products = new ArrayList<>();
        request.products().forEach(productRequest -> {
            PlatziProductResponse platziProductResponse = productService.getProductById(productRequest.id());

            products.add(Product.builder()
                    .id(platziProductResponse.id())
                    .title(platziProductResponse.title())
                    .price(platziProductResponse.price())
                    .description(platziProductResponse.description())
                    .quantity(productRequest.quantity())
                    .build());
        });

        Basket basket = Basket.builder()
                .clientId(request.clientId())
                .status(Status.OPEN)
                .products(products)
                .build();

        basket.calculateTotalPrice();

        return repository.save(basket);
    }

    public Basket getBasketById(String id){
        Optional<Basket> optBasket = repository.findById(id);

        return optBasket
                .orElseThrow(() -> new IllegalArgumentException("Basket not Found"));

    }

    public Basket update(String id, BasketRequest request){
        Basket savedBasket = getBasketById(id);

        List<Product> products = new ArrayList<>();
        request.products().forEach(productRequest -> {
            PlatziProductResponse platziProductResponse = productService.getProductById(productRequest.id());

            products.add(Product.builder()
                        .id(platziProductResponse.id())
                        .title(platziProductResponse.title())
                        .price(platziProductResponse.price())
                        .description(platziProductResponse.description())
                        .quantity(productRequest.quantity())
                        .build());

                });


            savedBasket = Basket.builder()
                            .id(id)
                            .status(Status.OPEN)
                            .clientId(request.clientId())
                            .products(products)
                            .build();

            savedBasket.calculateTotalPrice();
            return repository.save(savedBasket);

    }

    public Basket payBasket(String id, PaymentRequest request){


        Basket savedBasket = getBasketById(id);
        savedBasket.setPaymentMethod(request.paymentMethod());
        savedBasket.setStatus(Status.SOLD);
        return repository.save(savedBasket);

    }

}
