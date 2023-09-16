package com.manuel.backend.usersapp.backendusersapp.repositories;

import com.manuel.backend.usersapp.backendusersapp.models.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    
}
