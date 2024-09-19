package istad.co.eleariningapi.features.lecture.dto;

public record LectureUpdateRequest(
        String description,

        String content,

        String duration
) {
}
