package istad.co.eleariningapi.features.lecture.dto;

import istad.co.eleariningapi.features.course.dto.CourseResponse;

public record LectureResponse(
        String alias,

        String title,

        String description,

        String content,

        String duration,

        Boolean status,

        CourseResponse course
) {
}
