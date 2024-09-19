package istad.co.eleariningapi.features.enrollment.dto;
import istad.co.eleariningapi.features.course.dto.CourseResponse;

import java.math.BigDecimal;

public record EnrollmentResponse(

        String alias,

        String enrollmentDate,

        BigDecimal progress,

        Boolean completionStatus,

        CourseResponse course

) {
}
