package com.mongle.api.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.mongle.api.exception.GeneralException;
import com.mongle.api.response.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadFile(String folderName, String fileName, MultipartFile multipartFile) throws IOException {
        String originalFileName = multipartFile.getOriginalFilename();

        if (originalFileName != null && !validateImageFileExtension(originalFileName)) {
            throw new GeneralException(ErrorStatus.UNSUPPORTED_FILE_TYPE);
        }

        File file = null;
        try {
            file = convertMultiPartToFile(multipartFile);
            String newFileName = folderName + "/" + fileName + getExtension(originalFileName);
            String uploadImageUrl = uploadFileToS3(newFileName, file);

            return uploadImageUrl;
        } catch (IOException e) {
            log.error("파일 업로드 중 오류 발생: {}", e.getMessage());
            throw new GeneralException(ErrorStatus.FILE_UPLOAD_FAILED);
        } finally {
            if (file != null && file.exists()) {
                removeNewFile(file);
            }
        }
    }

    private String uploadFileToS3(String fileName, File file) {
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        return amazonS3.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("임시 파일 삭제 성공");
        } else {
            log.warn("임시 파일 삭제 실패");
        }
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File tempFile = File.createTempFile("temp", getExtension(file.getOriginalFilename()));

        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(file.getBytes());
        }

        return tempFile;
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    // 파일 확장자 체크 (.jpg, .png만 허용)
    private boolean validateImageFileExtension(String fileName) {
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

        return fileExtension.equals("jpg") || fileExtension.equals("png");
    }
}
