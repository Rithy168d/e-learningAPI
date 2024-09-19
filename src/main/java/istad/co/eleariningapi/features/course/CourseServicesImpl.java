package istad.co.eleariningapi.features.course;

import istad.co.eleariningapi.domain.Category;
import istad.co.eleariningapi.domain.Course;
import istad.co.eleariningapi.features.category.CategoryRepository;
import istad.co.eleariningapi.features.course.dto.CourseCreateRequest;
import istad.co.eleariningapi.features.course.dto.CourseResponse;
import istad.co.eleariningapi.features.course.dto.CourseUpdateRequest;
import istad.co.eleariningapi.mapper.CourseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CourseServicesImpl implements CourseServices {

    //  inject categoryRepository
    private final CategoryRepository categoryRepository;

    //  inject courseRepository
    private final CourseRepository courseRepository;

    //  inject courseMapper
    private final CourseMapper courseMapper;

    @Override
    public CourseResponse createNew(CourseCreateRequest courseCreateRequest) {

        //  validate category is not exit
        Category category = categoryRepository
                .findByAlias(courseCreateRequest.categoryAlias())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Category has not been found"
                ));

        //  validate course is exit
        if (courseRepository.existsByAlias(courseCreateRequest.alias())){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "category already exists"
            );
        }


        //  transfer dto to domain model
        Course course = courseMapper.fromCourseCreateRequest(courseCreateRequest);
        course.setCategory(category);

        //  system generate
        course.setStatus(true);
        course.setLanguage("english");


        //  save record to database and get the last record back
        course = courseRepository.save(course);

        //  transfer from domain to dto
        return courseMapper.toCourseResponse(course);
    }

    @Override
    public Page<CourseResponse> findList(int pageNumber, int pageSize) {

        // sort record by id
        Sort sort = Sort.by(Sort.Direction.DESC, "id");

        // page request
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        // get record from table database
        Page<Course> courses = courseRepository.findAll(pageRequest);

        //  transfer from domain model to DTO
        return courses.map(courseMapper::toCourseResponse);
    }

    @Override
    public CourseResponse findByAlias(String alias) {
        Course course = courseRepository
                .findByAlias(alias)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Course has not been found"
                ));

        return courseMapper.toCourseResponse(course);
    }

    @Override
    public CourseResponse updateByAlias(String alias, CourseUpdateRequest courseUpdateRequest) {
        //  validation alias
        Course course = courseRepository
                .findByAlias(alias)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Course has not been found"
                ));

        //  mapper
        courseMapper.fromCourseUpdateRequest(courseUpdateRequest, course);

        //  update
        course = courseRepository.save(course);

        return courseMapper.toCourseResponse(course);
    }

    @Override
    public void hideCourse(String alias) {
        //  validate alias is not exit
        Course course = courseRepository
                .findByAlias(alias)
                .orElseThrow(()->(new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Course has not been found"
                )));

        //  set status
        course.setStatus(false);

        //  save into table
        courseRepository.save(course);
    }

    @Override
    public void deleteCourseByAlias(String alias) {

        //  validate alias is not exit
        Course course = courseRepository.findByAlias(alias)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Course has not been found"
                ));

        //  delete course from table database
        courseRepository.delete(course);
    }
}
