package istad.co.eleariningapi.mapper;

import istad.co.eleariningapi.domain.Course;
import istad.co.eleariningapi.features.course.dto.CourseCreateRequest;
import istad.co.eleariningapi.features.course.dto.CourseResponse;
import istad.co.eleariningapi.features.course.dto.CourseUpdateRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    //  map course domain model to course dto
    CourseResponse toCourseResponse(Course course);

    //  map from course domain model to courseCreateRequest dto
    Course fromCourseCreateRequest(CourseCreateRequest courseCreateRequest);

    // Partially map
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromCourseUpdateRequest(CourseUpdateRequest courseUpdateRequest,
                                 @MappingTarget Course course);




}
