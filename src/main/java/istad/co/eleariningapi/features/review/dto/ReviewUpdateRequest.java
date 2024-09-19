package istad.co.eleariningapi.features.review.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ReviewUpdateRequest(
        BigDecimal rating,

        String comment
) {
}
