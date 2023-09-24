package com.manuel.backend.usersapp.backendusersapp.services;

import com.manuel.backend.usersapp.backendusersapp.models.DTO.UserDTO;
import com.manuel.backend.usersapp.backendusersapp.models.entities.User;
import com.manuel.backend.usersapp.backendusersapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public BCryptPasswordEncoder getPasswordEncoder(){
        return this.passwordEncoder;
    }
    @Autowired
    private UserRepository userRepository;

    public UserRepository getUserRepository(){
        return this.userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return (List<User>) getUserRepository().findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return getUserRepository().findById(id);
    }

    @Override
    @Transactional
    public UserDTO save(User user) {

        String encryptedPassword = this.getPasswordEncoder().encode(user.getPassword());
        user.setPassword(encryptedPassword);
        User userCreated = this.getUserRepository().save(user);
        UserDTO returnUser = new UserDTO(userCreated.getUsername(), userCreated.getEmail());

        return returnUser;
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
    public Optional<User> update(UserDTO user, Long id) {
        Optional<User> userDB = this.findById(id);
        if (userDB.isPresent()) {
            User existingUser = userDB.orElseThrow();
            existingUser.setUsername(user.getUsername() != null ? user.getUsername() : existingUser.getUsername());
            existingUser.setEmail(user.getEmail() != null ? user.getEmail() : existingUser.getEmail());

            return Optional.ofNullable(this.saveNoEncode(existingUser));
        }
        return Optional.empty();
    }
}
