package istad.co.eleariningapi.features.enrollment;

import istad.co.eleariningapi.features.enrollment.dto.EnrollmentCreateRequest;
import istad.co.eleariningapi.features.enrollment.dto.EnrollmentResponse;
import istad.co.eleariningapi.features.enrollment.dto.EnrollmentUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    //  inject enrollmentServices
    private final EnrollmentServices enrollmentServices;

    //  endpoint create new
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    EnrollmentResponse createNew(@Valid @RequestBody EnrollmentCreateRequest enrollmentCreateRequest){
        return enrollmentServices.createNew(enrollmentCreateRequest);
    }

    //  endpoint findList
    @GetMapping
    Page<EnrollmentResponse> findList(@RequestParam(required = false, defaultValue = "0") int pageNumber,
                                      @RequestParam(required = false, defaultValue = "25") int pageSize){
        return enrollmentServices.findList(pageNumber, pageSize);
    }

    // endpoint findByAlias
    @GetMapping("/{alias}")
    EnrollmentResponse findByAlias(@PathVariable String alias){
        return enrollmentServices.findByAlias(alias);
    }

    //  endpoint hide enrollment
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{alias}/hide")
    void hideEnrollment(@PathVariable String alias){
        enrollmentServices.hideEnrollment(alias);
    }

    //  endpoint updateByAlias
    @PatchMapping("/{alias}")
    EnrollmentResponse updateByAlias(@PathVariable String alias,@RequestBody EnrollmentUpdateRequest enrollmentUpdateRequest){
        return enrollmentServices.updateByAlias(alias, enrollmentUpdateRequest);
    }
}
