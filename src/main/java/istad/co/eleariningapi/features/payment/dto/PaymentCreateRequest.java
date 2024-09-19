package istad.co.eleariningapi.features.payment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PaymentCreateRequest(

        @NotBlank(message = "alias is required")
        String alias,

        @NotNull(message = "amount is required")
        BigDecimal amount,

        @NotBlank(message = "payment method is required")
        String paymentMethod,

        @NotBlank(message = "course alias is required")
        String courseAlias,

        @NotBlank(message = "payment account owner is required")
        String userUuid




) {
}
