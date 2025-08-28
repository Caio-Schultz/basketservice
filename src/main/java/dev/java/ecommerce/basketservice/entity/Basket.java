package dev.java.ecommerce.basketservice.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.java.ecommerce.basketservice.enums.PaymentMethod;
import dev.java.ecommerce.basketservice.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

// Quando se usa mongoDB a anotation é @Document e é passo o nome da collection
@Document(collection = "basket")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Basket {

    // MongoDB salva id como String
    @Id
    private String id;
    private Long clientId;
    private BigDecimal totalPrice;
    private List<Product> products;
    private Status status;
    // Só irá incluir no corpo do response se esse valor não for nulo, ou seja, se o usuário colocar esse valor na requisição ele aparecerá no retorno, caso contrário não aparecerá
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PaymentMethod paymentMethod;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void calculateTotalPrice(){
        // Percorre a lista com stream. Para cada produto, pega o preço do produto e multiplica pela quantidade
        this.totalPrice = products.stream()
                .map(product -> product.getPrice().multiply(BigDecimal.valueOf(product.getQuantity()))) // Usa-se o metodo valueOf() para transformar a Quantity (inteiro) em um BigDecimal para que a precisão seja exata
                // Foi atribuído no metodo reduce() um Identity que é basicamente o valor inicial (nesse caso, ZERO). Após isso faz é feito uma operação de soma com o valor acumulado e o próximo BigDecimal da lista, ou seja, o próximo preço de produto
                // Ex: valor inicial = 0 e preco do próximo produto = 100, accumulatedPrice = 100; Preço do próximo produto = 200, accumulatedPrice = 300. A soma de todos os produtos da lista e o preço total do carrinho
                .reduce(BigDecimal.ZERO, (accumulatedPrice, productPrice) -> accumulatedPrice.add(productPrice));
    }

}
