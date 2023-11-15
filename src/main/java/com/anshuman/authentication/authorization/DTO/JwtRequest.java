package com.anshuman.authentication.authorization.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class JwtRequest {

    private String emailId;
    private String password;
}
