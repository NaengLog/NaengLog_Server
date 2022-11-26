package com.homework.naenglog.domain.post.exception;

import com.homework.naenglog.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class PostWrongException extends BusinessException {

    public static final PostWrongException EXCEPTION = new PostWrongException();

    private PostWrongException() {
        super(HttpStatus.FORBIDDEN, "접근 권한이 없는 게시글입니다");
    }
}
