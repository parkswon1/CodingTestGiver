package com.example.algoshuffer.repository;

import com.example.algoshuffer.entity.Problem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long>{
    boolean existsByProblemId(Long problemId);

    // 태그와 사용자의 id를 받아서 사용자가 푼 문제들을 찾아 반환하는 메서드
    @Query("SELECT p FROM Problem p JOIN p.userProblemMappings upm JOIN p.problemTagMappings ptm " +
            "WHERE upm.user.id = :userId AND ptm.problemTag.bojTagId = :tagId")
    List<Problem> findSolvedProblemsByUserAndTag(@Param("userId") Long userId, @Param("tagId") Long tagId);

    // 사용자가 해결한 문제를 제외하고 특정 태그가 있는 문제를 찾는 메세드
    // 평균 tier에 가까운 순서대로 정렬되고, 평균 tier에서 같은 거리에 있는 경우, 문제를 맞은 사용자 수가 많은 순서대로 정렬
    @Query("SELECT p FROM Problem p JOIN p.problemTagMappings ptm " +
            "WHERE ptm.problemTag.bojTagId = :tagId AND p.problemId NOT IN " +
            "(SELECT upm.problem.problemId FROM UserProblemMapping upm WHERE upm.user.id = :userId) " +
            "ORDER BY ABS(p.level - :averageTier) ASC, p.acceptedUserCount DESC")
    List<Problem> findProblemsByTagExcludingUserSolved(@Param("userId") Long userId, @Param("tagId") Long tagId, @Param("averageTier") int averageTier, Pageable pageable);

    // 사용자가 해결한 문제를 제외하고 사용자의 tier랑 비슷한 문제를 찾는 메서드
    // 사용자 티어에 2정도 차이나는 곳으로 문제를 묶고 문제를 맞은 사용자 수가 많은 순서대로 정렬
    @Query("SELECT p FROM Problem p WHERE p.level BETWEEN :tier - 2 AND :tier + 2 " +
            "AND p.problemId NOT IN (SELECT upm.problem.problemId FROM UserProblemMapping upm WHERE upm.user.id = :userId) " +
            "ORDER BY p.acceptedUserCount DESC")
    List<Problem> findProblemByUser(@Param("userId") Long userId, @Param("tier") int tier, Pageable pageable);
}