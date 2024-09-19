package istad.co.eleariningapi.features.fileupload;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/upload")
@RequiredArgsConstructor
public class FileUploadController {

    //  inject file uploadService
    private final FileUploadServices fileUploadServices;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{fileName}")
    void deleteByFileName(@PathVariable String fileName) {
        fileUploadServices.deleteByFileName(fileName);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/multiple")
    List<FileUploadResponse> uploadMultiple(@RequestPart List<MultipartFile> files) {
        System.out.println("Test upload multiple");
        return fileUploadServices.uploadMultiple(files);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    FileUploadResponse upload(@RequestPart MultipartFile file) {
        return fileUploadServices.upload(file);
    }

}
