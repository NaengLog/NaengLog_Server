package com.homework.naenglog.domain.post.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter @AllArgsConstructor
@Builder
public class PostResponse {

    private Long postId;

    private String title;

    private String content;

    private Long author_id;

    private String createdAt;

    private int view;

    private List<String> attachmentUrls;
}
