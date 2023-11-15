package com.anshuman.authentication.authorization.Repository;

import com.anshuman.authentication.authorization.Entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    /*
    To enable eager loading for the specified attributes at runtime in the entity.
    It handles the n+1 issue.
     */
//    @EntityGraph(attributePaths ={
//            "role"
//    })
    @EntityGraph(value = "UserWithRole")
    Optional<User> findByEmailId(String emailId);
}
