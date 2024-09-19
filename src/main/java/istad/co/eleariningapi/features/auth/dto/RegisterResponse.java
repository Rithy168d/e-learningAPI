package istad.co.eleariningapi.features.auth.dto;

import lombok.Builder;

@Builder
public record RegisterResponse(
        String message,

        String email
) {
}
