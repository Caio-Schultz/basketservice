package dev.java.ecommerce.basketservice.service;

import dev.java.ecommerce.basketservice.client.PlatziStoreClient;
import dev.java.ecommerce.basketservice.client.response.PlatziProductResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final PlatziStoreClient client;

    public ProductService(PlatziStoreClient client) {
        this.client = client;
    }

    // Usa-se @Cacheable para trabalhar com Redis
    // Aqui passa um valor para esse metodo
    @Cacheable(value = "products")
    public List<PlatziProductResponse> getAllProducts(){
        return client.getAllProducts();
    }

    // Aqui passa um valor e a chave que será entregue, no caso o id do produto
    @Cacheable(value = "product", key = "#id")
    public PlatziProductResponse getProductById(Long id){
        return client.getProductsById(id);
    }


}
