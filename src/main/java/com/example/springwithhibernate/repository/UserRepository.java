package com.example.springwithhibernate.repository;

import com.example.springwithhibernate.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
}
