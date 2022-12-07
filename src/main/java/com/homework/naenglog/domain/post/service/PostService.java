package com.homework.naenglog.domain.post.service;

import com.homework.naenglog.domain.auth.entity.User;
import com.homework.naenglog.domain.auth.facade.UserFacade;
import com.homework.naenglog.domain.post.entity.Post;
import com.homework.naenglog.domain.post.exception.PostNotFoundException;
import com.homework.naenglog.domain.post.exception.PostWrongException;
import com.homework.naenglog.domain.post.presentation.dto.CreatePostRequest;
import com.homework.naenglog.domain.post.presentation.dto.ModifyPostRequest;
import com.homework.naenglog.domain.post.presentation.dto.response.PostListResponse;
import com.homework.naenglog.domain.post.presentation.dto.response.PostResponse;
import com.homework.naenglog.domain.post.repository.PostRepository;
import com.homework.naenglog.domain.upload.entity.Upload;
import com.homework.naenglog.domain.upload.exception.AttachmentNotCoincidenceException;
import com.homework.naenglog.domain.upload.repository.UploadRepository;
import com.homework.naenglog.global.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UploadRepository uploadRepository;
    private final UserFacade userFacade;

    @Transactional(rollbackFor = Exception.class)
    public Long createPost(CreatePostRequest request) {
        User author = userFacade.queryUser(true);

        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .attachmentList(new ArrayList<>())
                .build();
        post.setAuthor(author);
        author.addPost(post);

        if(!request.getAttachments().isEmpty()) {
            List<Upload> list = request.getAttachments().stream().map(id -> uploadRepository.findById(id)
                    .orElseThrow(() -> AttachmentNotCoincidenceException.EXCEPTION)).collect(Collectors.toList());
            list.get(0).setPost(post);
            post.addAttachment(list);
        }

        return post.getPostId();

    }

    public PostResponse getPost(Long postId) {
        Post post = increaseViewPost(postId);

        return ResponseUtil.getResponse(post);
    }

    @Transactional(readOnly = true)
    public PostListResponse getPostByTitle(Pageable pageable, String title) {
        Page<Post> list = postRepository.findAllByTitleContains(pageable, title);

        return ResponseUtil.getListResponse(list.getContent());
    }

    @Transactional(readOnly = true)
    public PostListResponse getAllPost(Pageable pageable) {
        Page<Post> list = postRepository.findAll(pageable);

        return ResponseUtil.getListResponse(list.getContent());
    }

    @Transactional(rollbackFor = Exception.class)
    public Long modifyPost(Long postId, ModifyPostRequest request) {
        User author = userFacade.queryUser(true);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> PostNotFoundException.EXCEPTION);

        if(!post.getAuthor().equals(author)) throw PostWrongException.EXCEPTION;

        post.modifyPost(request.getTitle(), request.getContent(), LocalDateTime.now());

        return postRepository.save(post).getPostId();
    }

    @Transactional(rollbackFor = Exception.class)
    public void deletePost(Long postId) {
        User author = userFacade.queryUser(true);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> PostNotFoundException.EXCEPTION);
        if(!post.getAuthor().equals(author)) throw PostWrongException.EXCEPTION;

        author.getPostList().remove(post);
    }

    @Transactional(rollbackFor = Exception.class)
    public Post increaseViewPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> PostNotFoundException.EXCEPTION);
        post.increaseView();

        return postRepository.save(post);
    }
}
