package com.homework.naenglog.domain.auth.presentation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class SignUpRequest {

    private String userId;
    private String password;
    private String name;

}
