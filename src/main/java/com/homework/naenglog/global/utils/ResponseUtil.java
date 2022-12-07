package com.homework.naenglog.global.utils;

import com.homework.naenglog.domain.post.entity.Post;
import com.homework.naenglog.domain.post.presentation.dto.response.PostListResponse;
import com.homework.naenglog.domain.post.presentation.dto.response.PostResponse;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class ResponseUtil {

    public static PostListResponse getListResponse(List<Post> list) {
        return PostListResponse.builder()
                .list(list.stream().map(ResponseUtil::getResponse
                ).collect(Collectors.toList()))
                .build();
    }

    public static PostResponse getResponse(Post post) {
        return PostResponse.builder()
                .postId(post.getPostId()).title(post.getTitle())
                .content(post.getContent()).author(post.getAuthor().getName())
                .createdAt(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(post.getCreatedAt()))
                .view(post.getView())
                .attachmentUrls(post.getAttachmentUrls())
                .build();
    }

}
