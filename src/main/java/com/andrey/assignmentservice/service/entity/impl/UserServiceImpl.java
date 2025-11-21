package com.andrey.assignmentservice.service.entity.impl;

import com.andrey.assignmentservice.exception.UserExistsException;
import com.andrey.assignmentservice.model.User;
import com.andrey.assignmentservice.repository.UserRepository;
import com.andrey.assignmentservice.service.entity.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User save(User user) {
        if (exists(user.getId())) {
            throw new UserExistsException("User with id " + user.getId() + " already exists");
        }
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
    public User update(User user) {
        User userToUpdate = get(user.getId());
        userToUpdate.setIsActive(user.getIsActive());
        return userRepository.save(userToUpdate);
    }

    @Override
    public boolean exists(String userId) {
        return userRepository.existsById(userId);
    }
}
