package com.example.AWS.Controller;

import com.example.AWS.Dao.UserDetailsDao;
import com.example.AWS.Model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController
{
    @Autowired
    private UserDetailsDao userDao;

    @PostMapping("/UserDetails")
    public String addUserDetails(@RequestBody UserModel userModel) {
        return userDao.addUserDetails(userModel);
    }

    @GetMapping("/UserDetails/{key}")
    public UserModel getUserDetails(@PathVariable("key") String key) {
        return userDao.getUserDetails(key);
    }
}