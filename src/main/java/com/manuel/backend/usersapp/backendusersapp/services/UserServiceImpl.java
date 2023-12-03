package com.manuel.backend.usersapp.backendusersapp.services;

import com.manuel.backend.usersapp.backendusersapp.models.DTO.UserDTO;
import com.manuel.backend.usersapp.backendusersapp.models.entities.Role;
import com.manuel.backend.usersapp.backendusersapp.models.entities.User;
import com.manuel.backend.usersapp.backendusersapp.models.mapper.UserDTOMapper;
import com.manuel.backend.usersapp.backendusersapp.repositories.RoleRepository;
import com.manuel.backend.usersapp.backendusersapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.manuel.backend.usersapp.backendusersapp.enums.SecurityEnums.ROLE_ADMIN;
import static com.manuel.backend.usersapp.backendusersapp.enums.SecurityEnums.ROLE_USER;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public BCryptPasswordEncoder getPasswordEncoder() {
        return this.passwordEncoder;
    }

    @Autowired
    private UserRepository userRepository;

    public UserRepository getUserRepository() {
        return this.userRepository;
    }

    @Autowired
    private RoleRepository roleRepository;

    public RoleRepository getRoleRepository() {
        return this.roleRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        List<User> users = (List<User>) this.getUserRepository().findAll();

        return users.stream()
                .map(u -> UserDTOMapper.builder().setUser(u).build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDTO> findById(Long id) {
        return this.getUserRepository()
                .findById(id)
                .map(u -> UserDTOMapper.builder().setUser(u).build());
    }

    @Override
    @Transactional
    public UserDTO save(User user) {

        String encryptedPassword = this.getPasswordEncoder().encode(user.getPassword());
        user.setPassword(encryptedPassword);

        user.setRoles(getRoles(user));
        //User userCreated = this.getUserRepository().save(user);
        // new UserDTO(userCreated.getUsername(), userCreated.getEmail());
        return UserDTOMapper.builder().setUser(this.getUserRepository().save(user)).build();
    }

    @Transactional
    public User saveNoEncode(User user) {
        return getUserRepository().save(user);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        getUserRepository().deleteById(id);
    }

    @Override
    public Optional<UserDTO> update(UserDTO user, Long id) {
        Optional<User> userDB = this.getUserRepository().findById(id);
        User existingUser = null;
        if (userDB.isPresent()) {
            existingUser = userDB.orElseThrow();
            existingUser.setUsername(user.getUsername() != null ? user.getUsername() : existingUser.getUsername());
            existingUser.setEmail(user.getEmail() != null ? user.getEmail() : existingUser.getEmail());
            existingUser.setAdmin(user.isAdmin());
            existingUser.setRoles(getRoles(existingUser));
            this.getUserRepository().save(existingUser);
        }
        return Optional.ofNullable(UserDTOMapper.builder().setUser(existingUser).build());
    }

    private List<Role> getRoles(User user) {
        List<Role> roles = new ArrayList<>();
        Optional<Role> role = this.getRoleRepository().getRoleByName(ROLE_USER);
        if (role.isPresent()) {
            roles.add(role.orElseThrow());
        }
        if (user.isAdmin()) {
            Optional<Role> roleAdmin = this.getRoleRepository().getRoleByName(ROLE_ADMIN);
            if (roleAdmin.isPresent()) {
                roles.add(roleAdmin.orElseThrow());
            }
        }
        return roles;
    }
}
