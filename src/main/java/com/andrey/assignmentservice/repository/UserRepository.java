package com.andrey.assignmentservice.repository;

import com.andrey.assignmentservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    @Modifying
    @Query("UPDATE User u SET u.isActive = :isActive WHERE u.id = :userId")
    void updateUserStatus(@Param("userId") String userId, @Param("isActive") Boolean isActive);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.team where u.id = :userId")
    Optional<User> findWithTeam(@Param("userId") String userId);

    @Query("""
            SELECT u FROM User u
                         LEFT JOIN FETCH u.pullRequests pr
                         LEFT JOIN FETCH pr.author
                         where u.id = :userId
            """)
    Optional<User> findWithPullRequest(@Param("userId") String userId);

    @Query("""
            SELECT u FROM User u
            LEFT JOIN FETCH u.team t
            LEFT JOIN FETCH t.members
            WHERE u.id = :userId
            """)
    Optional<User> findWithTeamAndMembers(@Param("userId") String userId);
}
