package com.homework.naenglog.domain.comment.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter @AllArgsConstructor
@Builder
public class CommentListResponse {

    private List<CommentResponse>  list;

}
