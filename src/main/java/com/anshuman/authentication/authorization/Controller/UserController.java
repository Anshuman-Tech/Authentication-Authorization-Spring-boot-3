package com.anshuman.authentication.authorization.Controller;

import com.anshuman.authentication.authorization.Entity.User;
import com.anshuman.authentication.authorization.Service.UserService;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody User user){
        Optional<User> savedUser = userService.addUser(user);
        if(savedUser.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not created");
    }
}
