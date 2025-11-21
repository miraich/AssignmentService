package com.andrey.assignmentservice.repository;

import com.andrey.assignmentservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
