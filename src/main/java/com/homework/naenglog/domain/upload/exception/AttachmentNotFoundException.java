package com.homework.naenglog.domain.upload.exception;

import com.homework.naenglog.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class AttachmentNotFoundException extends BusinessException {
    public AttachmentNotFoundException() {
        super(HttpStatus.NOT_FOUND, "첨부파일을 찾지 못하였습니다");
    }
}
