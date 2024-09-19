package istad.co.eleariningapi.features.payment.dto;

import java.math.BigDecimal;

public record PaymentUpdateRequest(
        BigDecimal amount,

        String paymentMethod,

        String status

) {
}
