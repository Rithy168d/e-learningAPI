package istad.co.eleariningapi.features.course;

import istad.co.eleariningapi.features.course.dto.CourseCreateRequest;
import istad.co.eleariningapi.features.course.dto.CourseResponse;
import istad.co.eleariningapi.features.course.dto.CourseUpdateRequest;
import org.springframework.data.domain.Page;

public interface CourseServices {

    /**
     * Create new course
     * @param courseCreateRequest {@link CourseCreateRequest}
     * @return {@link CourseResponse}
     */
    CourseResponse createNew(CourseCreateRequest courseCreateRequest);


    /**
     * Find all course
     * @param pageNumber is required
     * @param pageSize is required
     * @return {@link CourseResponse}
     */
    Page<CourseResponse> findList(int pageNumber, int pageSize);

    /**
     * Find course by alias
     * @param alias is required
     * @return {@link CourseResponse}
     */
    CourseResponse findByAlias(String alias);

    /**
     * Update course by alias
     * @param alias is required
     * @param courseUpdateRequest {@link CourseUpdateRequest}
     * @return {@link CourseResponse}
     */
    CourseResponse updateByAlias(String alias, CourseUpdateRequest courseUpdateRequest);

    /**
     * Hide course
     * @param alias is required
     */
    void hideCourse(String alias);

    /**
     * Delete Course by alias
     * @param alias is required
     */
    void deleteCourseByAlias(String alias);


}
