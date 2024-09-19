package istad.co.eleariningapi.features.review;

import istad.co.eleariningapi.features.review.dto.ReviewCreateRequest;
import istad.co.eleariningapi.features.review.dto.ReviewResponse;
import istad.co.eleariningapi.features.review.dto.ReviewUpdateRequest;
import org.springframework.data.domain.Page;

public interface ReviewService {

    /**
     * Create new Review
     * @param reviewCreateRequest {@link ReviewCreateRequest}
     * @return {@link ReviewResponse}
     */
    ReviewResponse createNew(ReviewCreateRequest reviewCreateRequest);

    /**
     * Find list
     * @param pageNumber is required
     * @param pageSize is required
     * @return {@link ReviewResponse}
     */
    Page<ReviewResponse> findList(int pageNumber, int pageSize);

    /**
     * Update review by alias
     * @param alias is required
     * @param reviewUpdateRequest {@link ReviewUpdateRequest}
     * @return {@link ReviewResponse}
     */
    ReviewResponse updateByAlias(String alias, ReviewUpdateRequest reviewUpdateRequest);

    /**
     * Delete review by alias
     * @param alias is required
     */
    void deleteByAlias(String alias);

    /**
     * Find review by alias
     * @param alias is required
     * @return {@link ReviewResponse}
     */
    ReviewResponse findByAlias(String alias);
}
