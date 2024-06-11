package com.example.algoshuffer.service;

import com.example.algoshuffer.entity.Problem;
import com.example.algoshuffer.entity.User;
import com.example.algoshuffer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    //id로 user를 찾는 메소드
    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    //name으로 사용자의 정보 저장
    public void saveUserByName(User user){
        userRepository.save(user);
    }

}
