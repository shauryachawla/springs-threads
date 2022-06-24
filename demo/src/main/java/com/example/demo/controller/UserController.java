package com.example.demo.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.runnables.UserProcessor;
import com.example.demo.service.UserSerivce;

@RestController
@RequestMapping("/api")
class resourceNameController {

    @Autowired
    UserSerivce userService;

    @Autowired
    UserProcessor userProcessor;

    @PostMapping("/upload")
    public ResponseEntity<Integer> upload(@RequestParam("file") MultipartFile file) throws InterruptedException, ExecutionException {
        // threads to read each line and create a user object from them.
        // each thread calls service.save(user)
        int response=0;
        ExecutorService executor = Executors.newFixedThreadPool(3);
        try {
            List<String> users = getUsers(file);
            for(String user: users) {
                userProcessor.setUserRecord(user);
                response = executor.submit(userProcessor).get();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        executor.shutdown();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public List<String> getUsers(MultipartFile file) throws IOException {
        List<String> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            while (reader.ready()) {
                users.add(reader.readLine());
            }
        }
        return users;
    }

}