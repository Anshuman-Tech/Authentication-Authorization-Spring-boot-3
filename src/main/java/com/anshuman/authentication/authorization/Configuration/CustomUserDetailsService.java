package com.anshuman.authentication.authorization.Configuration;

import com.anshuman.authentication.authorization.Entity.User;
import com.anshuman.authentication.authorization.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //Get the user from the database.
        Optional<User> existingUser = userRepository.findByEmailId(username);
        if(existingUser.isPresent()){
            return org.springframework.security.core.userdetails.User.builder()
                    .username(existingUser.get().getEmailId())
                    .password(existingUser.get().getPassword())
                    .roles(existingUser.get().getRole().toArray(new String[0])) //The roles() takes a varArgs of String (String... varName).
            //A varArgs of any dataType takes any number of parameters of the same type.
                    //To pass a list of string to Varargs of String - > list.toArray(new String[0])
                    .build();
        }
        return null;
    }



}
