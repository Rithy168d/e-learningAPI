package istad.co.eleariningapi.features.review;

import istad.co.eleariningapi.domain.Course;
import istad.co.eleariningapi.domain.Review;
import istad.co.eleariningapi.domain.User;
import istad.co.eleariningapi.features.course.CourseRepository;
import istad.co.eleariningapi.features.review.dto.ReviewCreateRequest;
import istad.co.eleariningapi.features.review.dto.ReviewResponse;
import istad.co.eleariningapi.features.review.dto.ReviewUpdateRequest;
import istad.co.eleariningapi.features.user.UserRepository;
import istad.co.eleariningapi.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    //  inject ReviewRepository
    private final ReviewRepository reviewRepository;

    // inject UserRepository
    private final UserRepository userRepository;

    //  inject CourseRepository
    private final CourseRepository courseRepository;

    //  inject ReviewMapper
    private final ReviewMapper reviewMapper;

    @Override
    public ReviewResponse createNew(ReviewCreateRequest reviewCreateRequest) {

        //  validate user's uuid
        User user = userRepository
                .findByUuid(reviewCreateRequest.userUuid())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User has not been found!"
                ));

        //  validate course's alias
        Course course = courseRepository
                .findByAlias(reviewCreateRequest.courseAlias())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Course has not been found!"
                ));

        //  validate review is exit
        if (reviewRepository.existsByAlias(reviewCreateRequest.alias())){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Review is already in use!"
            );
        }

        //  transfer from dto to domain model
        Review review = reviewMapper.fromReviewCreateRequest(reviewCreateRequest);

        //  system generate
        review.setCreationDate(LocalDateTime.now());
        review.setCourse(course);
        review.setUser(user);

        // transfer from domain model to dto
        return reviewMapper.toReviewResponse(review);
    }

    @Override
    public Page<ReviewResponse> findList(int pageNumber, int pageSize) {

        //  sort record by id DESC
        Sort sort = Sort.by(Sort.Direction.DESC, "id");

        //  pageRequest from client
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        //  find record after client request
        Page<Review> reviews = reviewRepository.findAll(pageRequest);

        //  transfer from domain to dto
        return reviews.map(reviewMapper::toReviewResponse);
    }

    @Override
    public ReviewResponse updateByAlias(String alias, ReviewUpdateRequest reviewUpdateRequest) {

        //  validate alias
        Review review = reviewRepository
                .findByAlias(alias)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Review has not been found!"
                ));

        // transfer from dto to domain model
        reviewMapper.fromReviewUpdateRequest(reviewUpdateRequest,review);

        // update to table database
        review = reviewRepository.save(review);

        return reviewMapper.toReviewResponse(review);
    }

    @Override
    public void deleteByAlias(String alias) {

        // validate review's alias
        Review review = reviewRepository
                .findByAlias(alias)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Review has not been found!"
                ));

        //  delete from table database
        reviewRepository.delete(review);
    }

    @Override
    public ReviewResponse findByAlias(String alias) {

        //  validate review's alias
        Review review = reviewRepository
                .findByAlias(alias)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Review has not been found!"
                ));

        //  transfer from domain model to dto
        return reviewMapper.toReviewResponse(review);
    }
}
