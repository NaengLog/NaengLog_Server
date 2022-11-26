package com.homework.naenglog.domain.post.presentation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class ModifyPostRequest {

    private String title;

    private String content;

    private String author;

}
