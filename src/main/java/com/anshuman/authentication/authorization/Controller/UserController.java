package com.anshuman.authentication.authorization.Controller;

import com.anshuman.authentication.authorization.Configuration.CustomUserDetailsService;
import com.anshuman.authentication.authorization.DTO.JwtRequest;
import com.anshuman.authentication.authorization.DTO.JwtResponse;
import com.anshuman.authentication.authorization.Entity.RefreshToken;
import com.anshuman.authentication.authorization.Entity.User;
import com.anshuman.authentication.authorization.Repository.RefreshTokenRepository;
import com.anshuman.authentication.authorization.Security.JwtHelper;
import com.anshuman.authentication.authorization.Security.RefreshTokenService;
import com.anshuman.authentication.authorization.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private AuthenticationManager authenticationManager;
    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody User user){
        Optional<User> savedUser = userService.addUser(user);
        if(savedUser.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not created");
    }

    @GetMapping("/get")

    /*
    We can hasAuthority() for single role, and hasAnyAuthority() for multiple roles.
    ROLE_ is appended by default, so we need to make the check for role with ROLE_RoleNameInDatabase.
     */
    @PreAuthorize("hasAuthority('ROLE_Admin_User')")
    public ResponseEntity<List<User>> getUsers(){
        Optional<List<User>> listOfUsers = userService.getUsers();
        if(listOfUsers.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(listOfUsers.get());
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/loggedIn")
    public ResponseEntity<String> getLoggedInUser(Principal principal){
        return ResponseEntity.status(HttpStatus.OK).body(principal.getName());
    }


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {

        this.doAuthenticate(request.getEmailId(), request.getPassword());

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getEmailId());
//        System.out.println("the returned user in the login api" + userDetails.getUsername()+ " " + userDetails.getPassword()+ " "+userDetails.getAuthorities());
        //Get the access token created.
        String token = this.jwtHelper.generateToken(userDetails);

        //Get the refresh token created.
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername()).getBody();

        JwtResponse response = JwtResponse.builder()
                .accessToken(token)
                .userName(userDetails.getUsername())
                .refreshToken(refreshToken.getToken())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            authenticationManager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }
    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }
}
