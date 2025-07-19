package com.skax.eatool.user.service.port.file.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;
import com.skax.eatool.user.dto.ApiResponse;
import com.skax.eatool.user.dto.ExceptionDto;
import com.skax.eatool.user.service.port.file.controller.port.FileServicePort;
import com.skax.eatool.user.service.port.file.domain.FileDownload;
import com.skax.eatool.user.service.port.file.domain.FileModel;
import com.skax.eatool.user.service.port.file.controller.request.FileDownloadRequest;
import com.skax.eatool.user.service.port.file.controller.response.FileUploadResponse;

import java.io.IOException;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class FileController {

    private final FileServicePort fileServicePort;

    /**
     * 파일 단일 업로드 API
     * 
     * @param file      업로드할 파일
     * @param policyKey 업로드 정책 키 (예: "imagePolicy")
     * @return 업로드된 파일 정보
     */
    @PostMapping("/upload")
    public ApiResponse<FileUploadResponse> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("policyKey") String policyKey) {

        log.info("[FileController] uploadFile START - fileName: {}, policyKey: {}", file.getOriginalFilename(),
                policyKey);

        try {
            FileModel uploadedFileModel = fileServicePort.storeFile(file, policyKey);

            log.info("[FileController] uploadFile END - fileId: {}", uploadedFileModel.getId());
            return ApiResponse.ok(FileUploadResponse.from(uploadedFileModel));
        } catch (IOException e) {
            log.error("[FileController] uploadFile END - IOException: {}", e.getMessage());
            return ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR,
                    ExceptionDto.builder().message("파일 업로드 중 오류가 발생했습니다.").build());
        }
    }

    /**
     * 다중 파일 업로드 API
     * 
     * @param files     업로드할 파일 리스트
     * @param policyKey 업로드 정책 키 (예: "documentPolicy")
     * @return 업로드된 파일들의 정보
     */
    @PostMapping("/upload/multiple")
    public ApiResponse<List<FileUploadResponse>> uploadMultipleFiles(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("policyKey") String policyKey) {

        log.info("[FileController] uploadMultipleFiles START - fileCount: {}, policyKey: {}", files.size(), policyKey);

        try {
            List<FileModel> uploadFileModels = fileServicePort.storeFiles(files, policyKey);

            log.info("[FileController] uploadMultipleFiles END - uploadedCount: {}", uploadFileModels.size());
            return ApiResponse.ok(uploadFileModels.stream().map(FileUploadResponse::from).toList());
        } catch (IOException e) {
            log.error("[FileController] uploadMultipleFiles END - IOException: {}", e.getMessage());
            return ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR,
                    ExceptionDto.builder().message("파일 업로드 중 오류가 발생했습니다.").build());
        }
    }

    /**
     * 파일 다운로드 API
     * 
     * @param fileDownloadRequest 파일다운로드 요청 객체
     * @return 파일 Resource
     */

    @PostMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestBody FileDownloadRequest fileDownloadRequest) {

        log.info("[FileController] downloadFile START - fileId: {}, dirPath: {}, orgName: {}",
                fileDownloadRequest.getFileId(), fileDownloadRequest.getDirPath(), fileDownloadRequest.getOrgName());

        FileDownload fileDownload = fileServicePort.getFileDownload(fileDownloadRequest.toModel());
        if (fileDownload != null) {
            // URL 인코딩된 한글 파일명
            String encodedFileName = UriUtils.encode(fileDownload.fileName(), UTF_8);

            HttpHeaders headers = new HttpHeaders();
            headers.add(CONTENT_DISPOSITION, String.format("attachment; filename=%s", encodedFileName));
            headers.add(CONTENT_TYPE, APPLICATION_OCTET_STREAM_VALUE);

            log.info("[FileController] downloadFile END - fileName: {}", fileDownload.fileName());
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(fileDownload.resource());
        }

        log.warn("[FileController] downloadFile END - file not found");
        return null;
    }

}
