package com.anshuman.authentication.authorization.Service;

import com.anshuman.authentication.authorization.Entity.User;
import com.anshuman.authentication.authorization.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.process.internal.UserTypeResolution;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    /*
    * @RequiredArgsConstructor is used to achieve Constructor based dependency injection. It creates a constructor with the required arguments.
    * The arguments for fields must be final.
    */
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public Optional<User> addUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return Optional.of(userRepository.save(user));
    }
}
