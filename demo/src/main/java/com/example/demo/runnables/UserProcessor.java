package com.example.demo.runnables;

import java.util.StringTokenizer;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.User;
import com.example.demo.service.UserSerivce;

@Service
public class UserProcessor implements Callable<Integer> {
    @Autowired
    UserSerivce userSerivce;
    String userRecord;
    int rows;

    public void setUserRecord(String userRecord) {
        this.userRecord = userRecord;
    }
    public String getUserRecord() {
        return this.userRecord;
    }

    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName() + " processing.");
        StringTokenizer tokenizer = new StringTokenizer(userRecord, ",");
        User user = null;
        while (tokenizer.hasMoreTokens()) {
            user = new User();
            user.setName(tokenizer.nextToken());
            user.setEmail(tokenizer.nextToken());
            rows += userSerivce.saveUser(user);
        }
        return rows;
    }

}
