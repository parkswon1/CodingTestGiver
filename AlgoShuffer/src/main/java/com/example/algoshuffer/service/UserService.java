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

    //이름으로 회원 정보 찾기
    public User findByName(String name) {
        Optional<User> userOptional = userRepository.findByUsername(name);
        return userOptional.orElse(null);
    }

    //사용자의 정보 저장
    public void saveUser(User user) {
        userRepository.save(user);
    }
}