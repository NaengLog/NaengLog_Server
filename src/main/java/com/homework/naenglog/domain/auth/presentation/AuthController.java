package com.homework.naenglog.domain.auth.presentation;

import com.homework.naenglog.domain.auth.presentation.dto.SignInRequest;
import com.homework.naenglog.domain.auth.presentation.dto.SignUpRequest;
import com.homework.naenglog.domain.auth.presentation.dto.response.LoginResponse;
import com.homework.naenglog.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(
            @RequestBody SignUpRequest request
    ) {
        authService.signUp(request);
    }

    @PostMapping("/sign-in")
    public LoginResponse signIn(
            @RequestBody SignInRequest request
    ) {
        return authService.signIn(request);
    }

}
