package com.andrey.assignmentservice.service.entity.impl;

import com.andrey.assignmentservice.exception.UserExistsException;
import com.andrey.assignmentservice.model.User;
import com.andrey.assignmentservice.repository.UserRepository;
import com.andrey.assignmentservice.service.entity.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User create(User user) {
        if (exists(user.getId())) {
            throw new UserExistsException("User with id " + user.getId() + " already exists");
        }
        return save(user);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User get(String userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException(
                        String.format("User with id %s isn't found", userId)
                ));
    }

    @Override
    public User getWithPullRequest(String userId) {
        return userRepository.findWithPullRequest(userId).orElseThrow(
                () -> new EntityNotFoundException(
                        String.format("User with id %s isn't found", userId)
                )
        );
    }

    @Override
    public User getWithTeam(String userId) {
        return userRepository.findWithTeam(userId).orElseThrow(
                () -> new EntityNotFoundException(
                        String.format("User with id %s isn't found", userId)
                )
        );
    }

    @Override
    public User getWithTeamAndMembers(String userId) {
        return userRepository.findWithTeamAndMembers(userId).orElseThrow(
                () -> new EntityNotFoundException(
                        String.format("User with id %s isn't found", userId)
                )
        );
    }

    @Override
    @Transactional
    public User updateStatus(User user) {
        userRepository.updateUserStatus(user.getId(), user.getIsActive());
        return getWithTeam(user.getId());
    }

    @Override
    public boolean exists(String userId) {
        return userRepository.existsById(userId);
    }
}
