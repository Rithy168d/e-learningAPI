package istad.co.eleariningapi.features.category.dto;

public record CategoryUpdateRequest(
    String description,
    Boolean status
) {
    
}
