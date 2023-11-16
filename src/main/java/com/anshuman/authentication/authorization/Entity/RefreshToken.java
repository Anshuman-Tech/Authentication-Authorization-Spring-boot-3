package com.anshuman.authentication.authorization.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator",sequenceName = "sequenceGenerator")
    private Long id;
    private String token;
    private LocalDateTime expiryDate;

    /*
    Note - The default fetch types of the Mapping in hibernate are:
    @OneToOne() -> Eager
    @ManyToOne() -> Eager
    @OneToMany() -> Lazy
    @ManyToMany() -> Lazy
     */
    @ManyToOne() //By Default eager, and need it to be loaded eagerly.
    @JoinColumn(name = "user_id",referencedColumnName = "userId")
    private User user;
}
