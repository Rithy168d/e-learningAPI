package istad.co.eleariningapi.features.enrollment.dto;

import java.math.BigDecimal;

public record EnrollmentUpdateRequest(
        String enrollmentDate,

        BigDecimal progress
) {
}
