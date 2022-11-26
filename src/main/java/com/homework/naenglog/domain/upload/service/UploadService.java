package com.homework.naenglog.domain.upload.service;

import com.homework.naenglog.domain.post.repository.PostRepository;
import com.homework.naenglog.domain.upload.entity.Upload;
import com.homework.naenglog.domain.upload.exception.AttachmentFailedSaveException;
import com.homework.naenglog.domain.upload.exception.AttachmentNotFoundException;
import com.homework.naenglog.domain.upload.repository.UploadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UploadService {

    private final PostRepository postRepository;
    private final UploadRepository uploadRepository;

    @Transactional(rollbackFor = RuntimeException.class)
    public Long uploadAttachment(MultipartFile file) {

        try {
            Upload attachment = Upload.builder()
                    .clientDownloadName(file.getName())
                    .content(file.getBytes())
                    .build();

            return uploadRepository.save(attachment).getUploadId();
        } catch (IOException e) {
            throw AttachmentFailedSaveException.EXCEPTION;
        }
    }

    @Transactional(readOnly = true, rollbackFor = RuntimeException.class)
    public ResponseEntity<byte[]> getAttachment(Long id) {
        Upload upload = uploadRepository.findById(id)
                .orElseThrow(AttachmentNotFoundException::new);

        String contentDisposition = String.format("attachment; filename=\"%s\"", upload.getClientDownloadName());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(upload.getContent());
    }
}
