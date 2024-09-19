package istad.co.eleariningapi.features.course.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CourseCreateRequest(
        @NotBlank(message = "alias is required")
        String alias,

        @NotBlank(message = "title is required")
        String title,

        @NotBlank(message = "description is required")
        String description,

        @NotNull(message = "price is required")
        BigDecimal price,

        @NotBlank(message = "level is required")
        String level,

        @NotBlank(message = "categories is required")
        String categoryAlias


) {
}
