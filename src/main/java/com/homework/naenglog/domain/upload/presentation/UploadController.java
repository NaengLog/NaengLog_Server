package com.homework.naenglog.domain.upload.presentation;

import com.homework.naenglog.domain.upload.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
public class UploadController {

    private final UploadService uploadService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/attachments")
    public Long uploadAttachment(
            @RequestParam(value = "preview", required = false) MultipartFile file
    ) {
        return uploadService.uploadAttachment(file);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping(value = "/attachments/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> getAttachment(
            @PathVariable("id") Long id
    ) {
        return uploadService.getAttachment(id);
    }

}
