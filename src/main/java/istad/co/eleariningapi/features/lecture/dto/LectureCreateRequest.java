package istad.co.eleariningapi.features.lecture.dto;

import jakarta.validation.constraints.NotBlank;

public record LectureCreateRequest(
        @NotBlank(message = "Alias is required")
        String alias,

        @NotBlank(message = "Title is required")
        String title,

        @NotBlank(message = "Description is required")
        String description,

        @NotBlank(message = "Content is required")
        String content,

        @NotBlank(message = "Duration is required")
        String duration,

        @NotBlank(message = "Course is required")
        String courseAlias
) {
}
