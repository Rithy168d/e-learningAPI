package istad.co.eleariningapi.features.lecture;

import istad.co.eleariningapi.features.lecture.dto.LectureCreateRequest;
import istad.co.eleariningapi.features.lecture.dto.LectureResponse;
import istad.co.eleariningapi.features.lecture.dto.LectureUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/lectures")
@RequiredArgsConstructor
public class LectureController {

    //  inject lectureService
    private final LectureService lectureService;

    //  endpoint create new
    @PostMapping
    LectureResponse createNew(@Valid @RequestBody LectureCreateRequest lectureCreateRequest){
        return lectureService.createNew(lectureCreateRequest);
    }

    //  endpoint findList
    @GetMapping
    Page<LectureResponse> findList(@RequestParam(required = false, defaultValue = "0") int pageNumber,
                                   @RequestParam(required = false, defaultValue = "25") int pageSize){
        return lectureService.findList(pageNumber, pageSize);
    }

    //  endpoint updateByAlias
    @PatchMapping("/{alias}")
    LectureResponse updateByAlias(@PathVariable String alias,
                                  @Valid @RequestBody LectureUpdateRequest lectureUpdateRequest){
        return lectureService.updateByAlias(alias, lectureUpdateRequest);
    }

    //  endpoint hideLecture
    @PutMapping("/{alias}/hide")
    void hideLecture(@PathVariable String alias){
        lectureService.hideLecture(alias);
    }

    //  endpoint findByAlis
    @GetMapping("/{alias}")
    LectureResponse findByAlias(@PathVariable String alias){
        return lectureService.findByAlias(alias);
    }
}
