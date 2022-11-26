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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
                    .orElseThrow(AttachmentNotCoincidenceException::new)).collect(Collectors.toList());
            list.get(0).setPost(post);
            post.addAttachment(list);
        }

        return post.getPostId();

    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long postId) {
        increaseViewPost(postId);
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        return PostResponse.builder()
                .postId(post.getPostId()).title(post.getTitle())
                .content(post.getContent()).author_id(post.getAuthor().getId())
                .createdAt(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(post.getCreatedAt()))
                .view(post.getView())
                .attachmentUrls(post.getAttachmentUrls())
                .build();
    }

    @Transactional(readOnly = true)
    public PostListResponse getAllPost(Pageable pageable) {
        Page<Post> list = postRepository.findAll(pageable);

        return PostListResponse.builder()
                .list(list.stream().map(it ->
                        PostResponse.builder()
                                .postId(it.getPostId()).title(it.getTitle())
                                .content(it.getContent()).author_id(it.getAuthor().getId())
                                .createdAt(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(it.getCreatedAt()))
                                .view(it.getView())
                                .attachmentUrls(it.getAttachmentUrls())
                                .build()
                ).collect(Collectors.toList()))
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    public Long modifyPost(Long postId, ModifyPostRequest request) {
        User author = userFacade.queryUser(true);

        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        if(post.getAuthor().equals(author)) throw PostWrongException.EXCEPTION;

        post.modifyPost(request.getTitle(), request.getContent(), LocalDateTime.now());

        return postRepository.save(post).getPostId();
    }

    @Transactional(rollbackFor = Exception.class)
    public void deletePost(Long postId) {
        User author = userFacade.queryUser(true);

        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
        if(post.getAuthor().equals(author)) throw PostWrongException.EXCEPTION;

        author.getPostList().remove(post);
    }

    public void increaseViewPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
        post.increaseView();

        postRepository.save(post);
    }
}
