package istad.co.eleariningapi.features.payment.dto;

import istad.co.eleariningapi.features.course.dto.CourseResponse;

import java.math.BigDecimal;

public record PaymentResponse(

        String alias,

        BigDecimal amount,

        String paymentMethod,

        String status,

        CourseResponse course

) {
}
