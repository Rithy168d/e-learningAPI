package istad.co.eleariningapi.features.course;

import istad.co.eleariningapi.features.course.dto.CourseCreateRequest;
import istad.co.eleariningapi.features.course.dto.CourseResponse;
import istad.co.eleariningapi.features.course.dto.CourseUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    //  inject course services
    private final CourseServices courseServices;

    //  endpoint create new course
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    CourseResponse createNew(@Valid @RequestBody CourseCreateRequest courseCreateRequest){
        return courseServices.createNew(courseCreateRequest);
    }

    //  endpoint find all course
    @GetMapping
    Page<CourseResponse> findAll(@RequestParam(required = false, defaultValue = "0") int pageNumber,
                                 @RequestParam(required = false, defaultValue = "25") int pageSize){
        return courseServices.findList(pageNumber, pageSize);
    }

    //  endpoint find by alias
    @GetMapping("/{alias}")
    CourseResponse findByAlias(@PathVariable String alias){
        return courseServices.findByAlias(alias);
    }

    //  endpoint update by alias
    @PatchMapping("/{alias}")
    CourseResponse updateByAlias(@PathVariable String alias, @RequestBody CourseUpdateRequest courseUpdateRequest){
        return courseServices.updateByAlias(alias, courseUpdateRequest);
    }

    //  hide course
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{alias}/hide")
    void hideCourse(@PathVariable("alias") String alias){
        courseServices.hideCourse(alias);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{alias}")
    void deleteCourseByAlias(@PathVariable("alias") String alias){
        courseServices.deleteCourseByAlias(alias);
    }
}
