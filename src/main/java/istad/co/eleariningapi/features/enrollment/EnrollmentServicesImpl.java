package istad.co.eleariningapi.features.enrollment;

import istad.co.eleariningapi.domain.Course;
import istad.co.eleariningapi.domain.Enrollment;
import istad.co.eleariningapi.domain.User;
import istad.co.eleariningapi.features.course.CourseRepository;
import istad.co.eleariningapi.features.enrollment.dto.EnrollmentCreateRequest;
import istad.co.eleariningapi.features.enrollment.dto.EnrollmentResponse;
import istad.co.eleariningapi.features.enrollment.dto.EnrollmentUpdateRequest;
import istad.co.eleariningapi.features.user.UserRepository;
import istad.co.eleariningapi.mapper.EnrollmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Service
@RequiredArgsConstructor
public class EnrollmentServicesImpl implements EnrollmentServices{

    //  inject enrollmentRepository
    private final EnrollmentRepository enrollmentRepository;

    //  inject enrollmentMapper
    private final EnrollmentMapper enrollmentMapper;

    //  inject courseRepository
    private final CourseRepository courseRepository;

    //  inject userRepository
    private final UserRepository userRepository;

    @Override
    public EnrollmentResponse createNew(EnrollmentCreateRequest enrollmentCreateRequest) {

        //  validate course is not exit
        Course course = courseRepository
                .findByAlias(enrollmentCreateRequest.courseAlias())
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Course has been not found"
                ));

        //  validate user is not exit
        User user = userRepository
                .findByUuid(enrollmentCreateRequest.userUuid())
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User is been not found"
                ));

        //  validation enrollment is exit
        if (enrollmentRepository.existsByAlias(enrollmentCreateRequest.alias())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Course has been already created"
            );
        }

        //  transfer from dto to domain model
        Enrollment enrollment = enrollmentMapper.fromEnrollmentCreateRequest(enrollmentCreateRequest);

        //  system generate
        enrollment.setCompletionStatus(true);

        // Create a DateTimeFormatter object with your desired format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();
        // Format the LocalDateTime object
        String formattedDateTime = now.format(formatter);
        // Set the enrollmentDate field with the formatted date time string
        enrollment.setEnrollmentDate(formattedDateTime);

        enrollment.setCourse(course);
        enrollment.setUser(user);

        //  save record to table database
        enrollment = enrollmentRepository.save(enrollment);

        //  transfer from domain model to dto
        return enrollmentMapper.toEnrollmentResponse(enrollment);
    }

    @Override
    public Page<EnrollmentResponse> findList(int pageNumber, int pageSize) {

        //  sort record by id DESC
        Sort sort = Sort.by(Sort.Direction.DESC, "id");

        //  page required from client
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        //  get record from table database
        Page<Enrollment> enrollments = enrollmentRepository.findAll(pageRequest);

        return enrollments.map(enrollmentMapper::toEnrollmentResponse);
    }

    @Override
    public EnrollmentResponse findByAlias(String alias) {

        //  validate alias
        Enrollment enrollment = enrollmentRepository
                .findByAlias(alias)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Enrollment with alias " + alias + " was not found"
                ));

        //  transfer from domain model to DTO
        return enrollmentMapper.toEnrollmentResponse(enrollment);
    }

    @Override
    public void hideEnrollment(String alias) {

        //  validate Enrollment is not exit
        Enrollment enrollment = enrollmentRepository
                .findByAlias(alias)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Enrollment with alias " + alias + " was not found"
                ));

        //  set status to false
        enrollment.setCompletionStatus(false);

        //  save to table database
        enrollmentRepository.save(enrollment);
    }

    @Override
    public EnrollmentResponse updateByAlias(String alias, EnrollmentUpdateRequest enrollmentUpdateRequest) {

        //  validate alias
        Enrollment enrollment = enrollmentRepository
                .findByAlias(alias)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Enrollment with alias " + alias + " was not found"
                ));

        //  transfer from DTO to domain model by mapping
        enrollmentMapper.fromEnrollmentUpdateRequest(enrollmentUpdateRequest, enrollment);

        //  update record to table database
        enrollment = enrollmentRepository.save(enrollment);
        return enrollmentMapper.toEnrollmentResponse(enrollment);
    }


}
