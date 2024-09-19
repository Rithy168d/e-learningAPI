package istad.co.eleariningapi.features.lecture;

import istad.co.eleariningapi.domain.Course;
import istad.co.eleariningapi.domain.Lecture;
import istad.co.eleariningapi.features.course.CourseRepository;
import istad.co.eleariningapi.features.lecture.dto.LectureCreateRequest;
import istad.co.eleariningapi.features.lecture.dto.LectureResponse;
import istad.co.eleariningapi.features.lecture.dto.LectureUpdateRequest;
import istad.co.eleariningapi.mapper.LectureMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class LectureServiceImpl implements LectureService {

    //  inject LectureRepository
    private final LectureRepository lectureRepository;

    //  inject CourseRepository
    private final CourseRepository courseRepository;

    //  inject LectureMapper
    private final LectureMapper lectureMapper;

    @Override
    public LectureResponse createNew(LectureCreateRequest lectureCreateRequest) {

        // validate course's alias
        Course course = courseRepository
                .findByAlias(lectureCreateRequest.courseAlias())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Course is not been found!"
                ));

        //  validate lecture's alias
        if (lectureRepository.existsByAlias(lectureCreateRequest.alias())){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Lecture already exists!"
            );
        }

        //  transfer from dto to domain model
        Lecture lecture = lectureMapper.fromLectureCreateRequest(lectureCreateRequest);

        //  system generate
        lecture.setStatus(true);
        lecture.setCourse(course);

        //  save
        lecture = lectureRepository.save(lecture);

        //  transfer from domain model to dto
        return lectureMapper.toLectureResponse(lecture);
    }

    @Override
    public Page<LectureResponse> findList(int pageNumber, int pageSize) {

        //  sort record by id DESC
        Sort sort = Sort.by(Sort.Direction.DESC, "id");

        //  page request from client
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        // find record from database
        Page<Lecture> lectures = lectureRepository.findAll(pageRequest);

        //  transfer from domain model to dto
        return lectures.map(lectureMapper::toLectureResponse);
    }

    @Override
    public LectureResponse updateByAlias(String alias, LectureUpdateRequest lectureUpdateRequest) {

        //  validate lecture's alias
        Lecture lecture = lectureRepository
                .findByAlias(alias)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Lecture is not been found!"
                ));

        //  transfer dto to domain model
        lectureMapper.fromLectureUpdateRequest(lectureUpdateRequest, lecture);

        //  save to table database
        lecture = lectureRepository.save(lecture);

        //  transfer from domain model to dto
        return lectureMapper.toLectureResponse(lecture);
    }

    @Override
    public void hideLecture(String alias) {

        //  validate lecture's alias
        Lecture lecture = lectureRepository
                .findByAlias(alias)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Lecture has not been found!"
                ));

        //  change status to false
        lecture.setStatus(false);

        //  save to table database
        lectureRepository.save(lecture);

    }

    @Override
    public LectureResponse findByAlias(String alias) {

        //  validate lecture's alias
        Lecture lecture = lectureRepository
                .findByAlias(alias)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Lecture has not been found!"
                ));

        //  transfer from domain model to dto
        return lectureMapper.toLectureResponse(lecture);
    }
}
