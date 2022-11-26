package com.homework.naenglog.domain.comment.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter @AllArgsConstructor
@Builder
public class CommentResponse {

    private String author;

    private String comment;

    private String createdAt;

}
