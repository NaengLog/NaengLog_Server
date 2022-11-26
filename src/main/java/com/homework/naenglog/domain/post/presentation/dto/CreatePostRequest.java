package com.homework.naenglog.domain.post.presentation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter @NoArgsConstructor
public class CreatePostRequest {

    private String title;

    private String content;

    private List<Long> attachments;

}
