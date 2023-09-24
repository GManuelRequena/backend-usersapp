package com.manuel.backend.usersapp.backendusersapp.services;

import static com.manuel.backend.usersapp.backendusersapp.enums.SecurityEnums.*;

import com.manuel.backend.usersapp.backendusersapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    public UserRepository getUserRepository(){
        return this.userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<com.manuel.backend.usersapp.backendusersapp.models.entities.User> o = this.getUserRepository().getUserByUsername(username);

        if(o.isEmpty()){
            throw new UsernameNotFoundException(String.format("Username %s does not exist.", username));
        }

        com.manuel.backend.usersapp.backendusersapp.models.entities.User user = o.orElseThrow();
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(ROLE_USER));

        return new User(user.getUsername(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                authorities);
    }
}
