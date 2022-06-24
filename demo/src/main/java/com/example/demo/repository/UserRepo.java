package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.dto.User;

public interface UserRepo extends CrudRepository<User, Integer> {

}