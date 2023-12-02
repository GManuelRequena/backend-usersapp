package com.manuel.backend.usersapp.backendusersapp.repositories;

import com.manuel.backend.usersapp.backendusersapp.models.entities.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {

    @Query("SELECT r FROM Role r WHERE r.name = ?1")
    Optional<Role> getRoleByName(String roleName);
}
