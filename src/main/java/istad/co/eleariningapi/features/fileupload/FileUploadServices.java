package istad.co.eleariningapi.features.fileupload;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileUploadServices {

    /**
     * Delete file
     * @param fileName is required
     */
    void deleteByFileName(String fileName);

    /**
     * Upload multiple file
     * @param files {@link List<MultipartFile>}
     * @return {@link List<FileUploadResponse>}
     */
    List<FileUploadResponse> uploadMultiple(List<MultipartFile> files);

    /**
     * Upload file
     * @param file {@link MultipartFile}
     * @return {@link FileUploadResponse}
     */
    FileUploadResponse upload(MultipartFile file);
}
