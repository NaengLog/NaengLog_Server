package com.homework.naenglog.domain.comment.entity;

import com.homework.naenglog.domain.post.entity.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
    public void setPost(Post post) {
        this.post = post;
    }

    private String author;

    private String comment;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder
    public Comment(String author, String comment) {
        this.author = author;
        this.comment = comment;
    }
}
