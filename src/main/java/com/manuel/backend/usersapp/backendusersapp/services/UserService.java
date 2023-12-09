package com.manuel.backend.usersapp.backendusersapp.services;

import com.manuel.backend.usersapp.backendusersapp.models.DTO.UserDTO;
import com.manuel.backend.usersapp.backendusersapp.models.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDTO> findAll();
    Optional<UserDTO> findById(Long id);
    UserDTO save(User user);
    Optional<UserDTO> update(UserDTO user, Long id);
    void remove(Long id);
    Page<UserDTO> findAll(Pageable pageable);
}

