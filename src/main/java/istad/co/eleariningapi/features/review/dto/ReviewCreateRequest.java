package istad.co.eleariningapi.features.review.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ReviewCreateRequest(
        @NotBlank(message = "Alias is require")
        String alias,

        @NotNull(message = "Rating is required")
        BigDecimal rating,

        @NotBlank(message = "Comment is required")
        String comment,

        @NotBlank(message = "Course is required")
        String courseAlias,

        @NotBlank(message = "User is required")
        String userUuid
) {
}
