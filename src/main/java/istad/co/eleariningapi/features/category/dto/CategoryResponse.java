package istad.co.eleariningapi.features.category.dto;

import lombok.Builder;

@Builder
public record CategoryResponse(
    String alias,
    String name,
    String description,
    Boolean status
) {
    
}
