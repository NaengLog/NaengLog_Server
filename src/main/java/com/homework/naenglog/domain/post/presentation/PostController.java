package com.homework.naenglog.domain.post.presentation;

import com.homework.naenglog.domain.post.presentation.dto.CreatePostRequest;
import com.homework.naenglog.domain.post.presentation.dto.ModifyPostRequest;
import com.homework.naenglog.domain.post.presentation.dto.response.PostListResponse;
import com.homework.naenglog.domain.post.presentation.dto.response.PostResponse;
import com.homework.naenglog.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/write")
    public Long creatPost(
            @RequestBody CreatePostRequest request
    ) {
        log.info("title : " +request.getTitle());
        log.info("content : " +request.getContent());
        log.info("attachments : " + request.getAttachments().toString());
        return postService.createPost(request);
    }

    @GetMapping("/list")
    public PostListResponse getAllPost(Pageable pageable) {
        return postService.getAllPost(pageable);
    }

    @GetMapping("/view/{post-id}")
    public PostResponse getPost(
            @PathVariable("post-id") Long postId
    ) {
        return postService.getPost(postId);
    }

    @PatchMapping("/update/{post-id}")
    public Long modifyPost(
            @PathVariable("post-id") Long postId,
            @RequestBody ModifyPostRequest request
    ) {
        return postService.modifyPost(postId, request);
    }

    @DeleteMapping("/{post-id}")
    public void deletePost(
            @PathVariable("post-id") Long postId
    ) {
         postService.deletePost(postId);
    }

}
