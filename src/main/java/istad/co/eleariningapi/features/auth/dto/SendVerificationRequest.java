package istad.co.eleariningapi.features.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record SendVerificationRequest(
        @NotBlank(message = "Email is required")
        String email
) {
}
