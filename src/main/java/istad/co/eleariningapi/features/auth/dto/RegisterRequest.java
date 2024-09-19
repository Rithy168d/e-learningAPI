package istad.co.eleariningapi.features.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record RegisterRequest(

        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Email is required")
        String email,

        @NotBlank(message = "Password is required")
        @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",
                message = "Password must contain minimum 8 characters in length, " +
                        "at least one uppercase English letter, " +
                        "at least one lowercase English letter, " +
                        "at least one digit," +
                        "at least one special character.") // Regular Expression
        String password,

        @NotBlank(message = "Confirmed Password is required")
        @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",
                message = "Password must contain minimum 8 characters in length, " +
                        "at least one uppercase English letter, " +
                        "at least one lowercase English letter, " +
                        "at least one digit," +
                        "at least one special character.") // Regular Expression
        String confirmedPassword,

        @NotBlank(message = "Pin required")
        String pin,

        @NotNull(message = "Term must be accepted")
        Boolean acceptTerm

        ) {
}
