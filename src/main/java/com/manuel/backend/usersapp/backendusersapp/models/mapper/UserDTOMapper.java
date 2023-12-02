package com.manuel.backend.usersapp.backendusersapp.models.mapper;

import com.manuel.backend.usersapp.backendusersapp.models.DTO.UserDTO;
import com.manuel.backend.usersapp.backendusersapp.models.entities.User;

public class UserDTOMapper {
    private User user;

    private UserDTOMapper() {
    }

    public static UserDTOMapper builder() {
        return new UserDTOMapper();
    }

    public UserDTOMapper setUser(User user) {
        this.user = user;
        return this;
    }

    public UserDTO build() {
        if (user == null) {
            throw new RuntimeException("Entity User cannot be null");
        }

        return new UserDTO(this.user.getId(), this.user.getUsername(), this.user.getEmail());

    }
}
