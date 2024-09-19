package istad.co.eleariningapi.mapper;

import istad.co.eleariningapi.domain.Enrollment;
import istad.co.eleariningapi.features.enrollment.dto.EnrollmentCreateRequest;
import istad.co.eleariningapi.features.enrollment.dto.EnrollmentResponse;
import istad.co.eleariningapi.features.enrollment.dto.EnrollmentUpdateRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface EnrollmentMapper {

    //  Map from Enrollment DTO to Enrollment Domain model
    Enrollment fromEnrollmentCreateRequest(EnrollmentCreateRequest enrollmentCreateRequest);

    //  Map Enrollment Domain model to EnrollmentResponse DTO
    EnrollmentResponse toEnrollmentResponse(Enrollment enrollment);

    //  Partially mapper
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromEnrollmentUpdateRequest(EnrollmentUpdateRequest enrollmentUpdateRequest,
                                     @MappingTarget Enrollment enrollment);
}
