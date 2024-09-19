package istad.co.eleariningapi.features.enrollment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record EnrollmentCreateRequest(
        @NotBlank(message = "Enrollment alias is required")
        String alias,

        @NotNull(message = "Progress is required")
        BigDecimal progress,

        @NotBlank(message = "Course is required")
        String courseAlias,

        @NotBlank(message = "Enrollment owner is required")
        String userUuid

) {
}
