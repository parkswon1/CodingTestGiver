package com.example.algoshuffer.service;

import com.example.algoshuffer.entity.User;
import com.example.algoshuffer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    //id로 user를 찾는 메소드
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findByName(String name) {
        Optional<User> userOptional = userRepository.findByUsername(name);
        return userOptional.orElse(null);
    }

    //name으로 사용자의 정보 저장
    public void saveUserByName(String name) {
        User user = new User();
        user.setUsername(name);
        userRepository.save(user);
    }
}