package com.homework.naenglog.domain.upload.entity;

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
public class Upload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uploadId;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
    public void setPost(Post post) {
        this.post = post;
    }

    private String clientDownloadName;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] content;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder
    public Upload(String clientDownloadName, byte[] content, LocalDateTime createdAt) {
        this.clientDownloadName = clientDownloadName;
        this.content = content;
        this.createdAt = createdAt;
    }
}
