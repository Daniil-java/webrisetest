package com.kuklin.webrisetest.repositories;

import com.kuklin.webrisetest.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsUserByEmail(String email);
}
