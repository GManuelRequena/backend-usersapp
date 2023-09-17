package com.manuel.backend.usersapp.backendusersapp.services;

import com.manuel.backend.usersapp.backendusersapp.models.DTO.UserDTO;
import com.manuel.backend.usersapp.backendusersapp.models.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();
    Optional<User> findById(Long id);
    User save(User user);
    Optional<User> update(UserDTO user, Long id);
    void remove(Long id);
}
