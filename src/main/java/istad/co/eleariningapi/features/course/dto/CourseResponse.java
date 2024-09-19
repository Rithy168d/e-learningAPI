package istad.co.eleariningapi.features.course.dto;

import istad.co.eleariningapi.features.category.dto.CategoryResponse;

import java.math.BigDecimal;

public record CourseResponse(
        String alias,

        String title,

        String description,

        BigDecimal price,

        String level,

        Boolean status,

        CategoryResponse category

) {
}
