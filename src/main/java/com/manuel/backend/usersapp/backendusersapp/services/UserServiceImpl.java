package com.manuel.backend.usersapp.backendusersapp.services;

import com.manuel.backend.usersapp.backendusersapp.models.entities.User;
import com.manuel.backend.usersapp.backendusersapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

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
    public User save(User user) {
        return getUserRepository().save(user);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        getUserRepository().deleteById(id);
    }

    @Override
    public Optional<User> update(User user, Long id) {
        Optional<User> userDB = this.findById(id);
        if (userDB.isPresent()) {
            User existingUser = userDB.orElseThrow();
            existingUser.setUserName(user.getUserName() != null ? user.getUserName() : existingUser.getUserName());
            existingUser.setEmail(user.getEmail() != null ? user.getEmail() : existingUser.getEmail());
            existingUser.setPassword(user.getPassword() != null ? user.getPassword() : existingUser.getPassword());

            return Optional.ofNullable(this.save(existingUser));
        }
        return Optional.empty();
    }
}
