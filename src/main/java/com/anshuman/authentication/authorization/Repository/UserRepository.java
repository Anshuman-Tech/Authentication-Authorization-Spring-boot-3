package com.anshuman.authentication.authorization.Repository;

import com.anshuman.authentication.authorization.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
