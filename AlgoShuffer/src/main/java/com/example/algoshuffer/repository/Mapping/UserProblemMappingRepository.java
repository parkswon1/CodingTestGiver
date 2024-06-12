package com.example.algoshuffer.repository.Mapping;

import com.example.algoshuffer.entity.Mapping.UserProblemMapping;

import com.example.algoshuffer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserProblemMappingRepository extends JpaRepository<UserProblemMapping, Long> {
    List<UserProblemMapping> findByUser(User user);
}
