package com.homework.naenglog.domain.auth.service;

import com.homework.naenglog.domain.auth.entity.User;
import com.homework.naenglog.domain.auth.exception.PasswordWrongException;
import com.homework.naenglog.domain.auth.exception.UserAlreadyExistsException;
import com.homework.naenglog.domain.auth.exception.UserNotFoundException;
import com.homework.naenglog.domain.auth.presentation.dto.SignInRequest;
import com.homework.naenglog.domain.auth.presentation.dto.SignUpRequest;
import com.homework.naenglog.domain.auth.presentation.dto.response.LoginResponse;
import com.homework.naenglog.domain.auth.repository.UserRepository;
import com.homework.naenglog.global.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional(rollbackFor = RuntimeException.class)
    public void signUp(SignUpRequest request) {
        userRepository.findByUserId(request.getUserId())
                .ifPresent(m -> {
                    throw UserAlreadyExistsException.EXCEPTION;
                });

        User user = User.builder()
                .userId(request.getUserId())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .build();
        userRepository.save(user);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public LoginResponse signIn(SignInRequest request) {
        User user = userRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> {throw UserNotFoundException.EXCEPTION;});

        if(passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return LoginResponse.builder()
                    .id(user.getId()).name(user.getName())
                    .token(jwtProvider.generateAccessToken(user.getId()))
                    .build();
        } else {
            throw PasswordWrongException.EXCEPTION;
        }
    }

}
