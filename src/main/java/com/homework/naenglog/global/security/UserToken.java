package com.homework.naenglog.global.security;

import com.homework.naenglog.domain.auth.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class UserToken extends UsernamePasswordAuthenticationToken {
    public UserToken(User user) {
        super(user, null, null);
    }
}
