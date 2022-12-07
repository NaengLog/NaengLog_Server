package com.homework.naenglog.domain.post.entity;

import com.homework.naenglog.domain.auth.entity.User;
import com.homework.naenglog.domain.comment.entity.Comment;
import com.homework.naenglog.domain.upload.entity.Upload;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    private String title;

    @Lob
    private String content;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;
    public void setAuthor(User author) {
        this.author = author;
    }

    @CreationTimestamp
    private LocalDateTime createdAt;

    private int view;

    public void increaseView() {
        this.view += 1;
    }

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Upload> attachmentList;
    public void addAttachment(List<Upload> file) {
        file.stream().map(it -> {
            this.attachmentList.add(it);
            return null;
        }).close();
    }
    public List<String> getAttachmentUrls() {
        return this.attachmentList.stream()
                .map(attachment -> String.format("/upload/attachments/%d", attachment.getUploadId()))
                .collect(Collectors.toList());
    }

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList;
    public void addComment(Comment comment) {
        this.commentList.add(comment);
    }

    @Builder
    public Post(String title, String content,
                List<Upload> attachmentList) {
        this.title = title;
        this.content = content;
        this.attachmentList = attachmentList;
    }

    public void modifyPost(String title, String content, LocalDateTime createdAt) {
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }
}
