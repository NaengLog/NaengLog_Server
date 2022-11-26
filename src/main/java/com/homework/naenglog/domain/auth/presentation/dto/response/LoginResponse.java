package com.homework.naenglog.domain.auth.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter @AllArgsConstructor
@Builder
public class LoginResponse {

    private String token;
    private Long id;
    private String name;

}
