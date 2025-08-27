package dev.java.ecommerce.basketservice.repository;

import dev.java.ecommerce.basketservice.entity.Basket;
import dev.java.ecommerce.basketservice.enums.Status;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
// Mesmo príncipio do Jpa, no entanto usando MongoDB
public interface BasketRepository extends MongoRepository<Basket, String> {

    Optional<Basket> findByClientIdAndStatus(Long clientId, Status status);

}
