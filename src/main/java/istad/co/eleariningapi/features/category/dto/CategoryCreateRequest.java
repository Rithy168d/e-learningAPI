package istad.co.eleariningapi.features.category.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryCreateRequest(
    @NotBlank(message = "alias is required")
    String alias,

    @NotBlank(message = "name is required")
    String name,

    @NotBlank(message = "description is required")
    String description

) {
    
}
