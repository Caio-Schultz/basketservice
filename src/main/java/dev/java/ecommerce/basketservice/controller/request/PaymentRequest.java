package dev.java.ecommerce.basketservice.controller.request;

import dev.java.ecommerce.basketservice.enums.PaymentMethod;

public record PaymentRequest(PaymentMethod paymentMethod) {


}
