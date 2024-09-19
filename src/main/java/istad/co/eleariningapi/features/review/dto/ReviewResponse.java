package istad.co.eleariningapi.features.review.dto;

import istad.co.eleariningapi.features.course.dto.CourseResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReviewResponse(
        String alias,

        BigDecimal rating,

        String comment,

        LocalDateTime creationDate,

        CourseResponse course
) {
}
