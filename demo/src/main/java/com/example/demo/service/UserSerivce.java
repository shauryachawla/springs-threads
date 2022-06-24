package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.User;
import com.example.demo.repository.UserRepo;

@Service
public class UserSerivce {
    @Autowired
    UserRepo repo;

    public int saveUser(User user) {
        return (user == repo.save(user))?1:0;
    }
}
