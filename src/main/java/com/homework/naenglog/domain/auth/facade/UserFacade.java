package com.homework.naenglog.domain.auth.facade;

import com.homework.naenglog.domain.auth.entity.User;
import com.homework.naenglog.domain.auth.exception.UserNotFoundException;
import com.homework.naenglog.domain.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User queryUser(boolean withPersistence) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(withPersistence) {
            return userRepository.findById(user.getId())
                    .orElseThrow(() -> UserNotFoundException.EXCEPTION);
        }else {
            return user;
        }
    }

    public User queryUser() {
        return queryUser(false);
    }

    public User queryUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);
    }

}
