package istad.co.eleariningapi.features.lecture;

import istad.co.eleariningapi.features.lecture.dto.LectureCreateRequest;
import istad.co.eleariningapi.features.lecture.dto.LectureResponse;
import istad.co.eleariningapi.features.lecture.dto.LectureUpdateRequest;
import org.springframework.data.domain.Page;

public interface LectureService {

    /**
     * Create new
     * @param lectureCreateRequest {@link LectureCreateRequest}
     * @return {@link LectureResponse}
     */
    LectureResponse createNew(LectureCreateRequest lectureCreateRequest);

    /**
     * Find list
     * @param pageNumber is required
     * @param pageSize is required
     * @return {@link LectureResponse}
     */
    Page<LectureResponse> findList(int pageNumber, int pageSize);

    /**
     * Update lecture by alias
     * @param alias is required
     * @param lectureUpdateRequest {@link LectureUpdateRequest}
     * @return {@link LectureResponse}
     */
    LectureResponse updateByAlias(String alias, LectureUpdateRequest lectureUpdateRequest);

    /**
     * Hide lecture
     * @param alias is required
     */
    void hideLecture(String alias);

    /**
     * Find lecture by alias
     * @param alias is required
     * @return {@link LectureResponse}
     */
    LectureResponse findByAlias(String alias);
}
