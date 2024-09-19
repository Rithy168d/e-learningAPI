package istad.co.eleariningapi.mapper;

import istad.co.eleariningapi.domain.Lecture;
import istad.co.eleariningapi.features.lecture.dto.LectureCreateRequest;
import istad.co.eleariningapi.features.lecture.dto.LectureResponse;
import istad.co.eleariningapi.features.lecture.dto.LectureUpdateRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface LectureMapper {

    //  map from dto to domain model
    Lecture fromLectureCreateRequest(LectureCreateRequest lectureCreateRequest);

    //  map from domain model to dto
    LectureResponse toLectureResponse(Lecture lecture);

    //  Partially map
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromLectureUpdateRequest(LectureUpdateRequest lectureUpdateRequest,
                                  @MappingTarget Lecture lecture);
}
