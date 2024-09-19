package istad.co.eleariningapi.features.enrollment;

import istad.co.eleariningapi.features.enrollment.dto.EnrollmentCreateRequest;
import istad.co.eleariningapi.features.enrollment.dto.EnrollmentResponse;
import istad.co.eleariningapi.features.enrollment.dto.EnrollmentUpdateRequest;
import org.springframework.data.domain.Page;

public interface EnrollmentServices {

    /**
     * Create new Enrollment
     * @param enrollmentCreateRequest {@link EnrollmentCreateRequest}
     * @return {@link EnrollmentResponse}
     */
    EnrollmentResponse createNew(EnrollmentCreateRequest enrollmentCreateRequest);

    /**
     * Find record by pagination
     * @param pageNumber is required
     * @param pageSize is required
     * @return {@link EnrollmentResponse}
     */
    Page<EnrollmentResponse> findList(int pageNumber, int pageSize);

    /**
     * Find record by alias
     * @param alias is required
     * @return {@link EnrollmentResponse}
     */
    EnrollmentResponse findByAlias(String alias);

    /**
     * Hide enrollment
     * @param alias is required
     */
    void hideEnrollment(String alias);

    /**
     * Update enrollment
     * @param alias is required
     * @param enrollmentUpdateRequest {@link EnrollmentUpdateRequest}
     * @return {@link EnrollmentResponse}
     */
    EnrollmentResponse updateByAlias(String alias, EnrollmentUpdateRequest enrollmentUpdateRequest);
}
