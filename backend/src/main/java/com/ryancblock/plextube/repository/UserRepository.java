package com.ryancblock.plextube.repository;

import com.ryancblock.plextube.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}