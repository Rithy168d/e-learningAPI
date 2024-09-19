package istad.co.eleariningapi.exception;

import lombok.Builder;

@Builder
public record FieldErrorResponse(
        String field,
        String detail
) {
}
