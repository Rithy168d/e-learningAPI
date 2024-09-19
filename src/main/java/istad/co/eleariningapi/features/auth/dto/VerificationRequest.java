package istad.co.eleariningapi.features.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record VerificationRequest(
        @NotBlank(message = "Email is required")
        String email,

        @NotBlank(message = "Verification code is required")
        String verifiedCode
) {
}
