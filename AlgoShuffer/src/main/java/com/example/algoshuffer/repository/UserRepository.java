package com.example.algoshuffer.repository;

import com.example.algoshuffer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT Count(u) FROM User u WHERE u.username = :username")
    public int findByName(@Param("username") String username);
}
