package istad.co.eleariningapi.mapper;

import istad.co.eleariningapi.domain.Review;
import istad.co.eleariningapi.features.review.dto.ReviewCreateRequest;
import istad.co.eleariningapi.features.review.dto.ReviewResponse;
import istad.co.eleariningapi.features.review.dto.ReviewUpdateRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    //  map dto to domain model
    Review fromReviewCreateRequest(ReviewCreateRequest reviewCreateRequest);

    // map from domain model to dto
    ReviewResponse toReviewResponse(Review review);

    //  Partially map
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromReviewUpdateRequest(ReviewUpdateRequest reviewUpdateRequest,
                                 @MappingTarget Review review);
}
