package com.userservice.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.List;

@RestController("/")
public class UserController {

    @Autowired
    UserDao userDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping
    public String createUser(){
        return "Welcome to Spring Reactive Application";
    }

    @GetMapping("/create-user")
    public UserBean createUser(@RequestBody UserBean userBean){
        userBean.setPassword(passwordEncoder.encode(userBean.getPassword()));
        return userDao.save(userBean);
    }

    @GetMapping("/user-list")
    public List<UserBean> getUsers(){
        return userDao.findAll();
    }

    @PreAuthorize("hasAuthority('S')") // If we have to use preauthorize then method return type must be mono or flux
    @GetMapping("/user/{userId}")
    public Mono<UserBean> getUsers(@PathVariable int userId) throws Throwable {
        return Mono.fromCallable(()-> {
            try {
                return userDao.findByUserId(userId).orElseThrow(Throwable::new);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        });
      //  return userDao.findByUserId(userId).orElseThrow(Throwable::new);
    }

    @PutMapping("/user/{userId}")
    public UserBean updateUser(@PathVariable int userId , @RequestBody UserBean userBean) throws Throwable {
        UserBean user = userDao.findByUserId(userId).orElseThrow(Throwable::new);
        user.setFullName(userBean.getFullName());
        user.setPassword(userBean.getPassword());
        user.setEmail(userBean.getEmail());
        //we can add more field as well
        return userDao.save(user);
    }
}
