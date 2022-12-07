package com.homework.naenglog.domain.comment.repository;

import com.homework.naenglog.domain.comment.entity.Comment;
import com.homework.naenglog.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByPost(Pageable pageable, Post post);
}
