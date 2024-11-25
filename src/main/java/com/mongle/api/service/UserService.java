package com.mongle.api.service;

import com.mongle.api.domain.User;
import com.mongle.api.exception.GeneralException;
import com.mongle.api.repository.UserRepository;
import com.mongle.api.response.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
    }

    public void updateProfileImage(User user, String fileUrl) {
        user.setProfileImageUrl(fileUrl);
        userRepository.save(user);
    }

    public void deleteProfileImage(User user) {
        user.setProfileImageUrl(null);
        userRepository.save(user);
    }
}
