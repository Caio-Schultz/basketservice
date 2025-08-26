package dev.java.ecommerce.basketservice.service;

import dev.java.ecommerce.basketservice.client.PlatziStoreClient;
import dev.java.ecommerce.basketservice.client.response.PlatziProductResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
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
        // Isso serve apenas para aparecer uma mensagem de log no terminal quando esse metodo for chamado
        // É apenas para mostrar que o caching de dados faz com que a Query seja feita apenas uma vez e depois ela é armazenada em memória durante o TTL
        log.info("Getting all products");
        return client.getAllProducts();
    }

    // Aqui passa um valor e a chave que será entregue, no caso o id do produto
    @Cacheable(value = "product", key = "#id")
    public PlatziProductResponse getProductById(Long id){
        log.info("Getting product with id: {}", id);
        return client.getProductsById(id);
    }


}
