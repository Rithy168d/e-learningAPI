package istad.co.eleariningapi.features.review;

import istad.co.eleariningapi.features.review.dto.ReviewCreateRequest;
import istad.co.eleariningapi.features.review.dto.ReviewResponse;
import istad.co.eleariningapi.features.review.dto.ReviewUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    //  inject service
    private final ReviewService reviewService;

    //  endpoint createNew
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    ReviewResponse createNew(@Valid @RequestBody ReviewCreateRequest reviewCreateRequest) {
        return reviewService.createNew(reviewCreateRequest);
    }

    // endpoint find list
    @GetMapping
    Page<ReviewResponse> findList(@RequestParam(required = false, defaultValue = "0") int pageNumber,
                                  @RequestParam(required = false, defaultValue = "25")  int pageSize){
        return reviewService.findList(pageNumber, pageSize);
    }

    //  endpoint update by alias
    @PatchMapping("/{alias}")
    ReviewResponse updateByAlias(@PathVariable String alias,
                                 @Valid @RequestBody ReviewUpdateRequest reviewUpdateRequest){
        return reviewService.updateByAlias(alias, reviewUpdateRequest);
    }

    //  endpoint delete by alias
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{alias}")
    void deleteByAlias(@PathVariable String alias){
        reviewService.deleteByAlias(alias);
    }

    //  endpoint find by alias
    @GetMapping("/{alias}")
    ReviewResponse findByAlias(@PathVariable String alias){
        return reviewService.findByAlias(alias);
    }
}
