package istad.co.eleariningapi.features.course.dto;

import java.math.BigDecimal;

public record CourseUpdateRequest(
        String description,

        BigDecimal price,

        Integer rating,

        String language,

        Boolean status

) {
}
