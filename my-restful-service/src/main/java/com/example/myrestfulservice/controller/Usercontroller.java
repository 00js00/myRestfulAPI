package com.example.myrestfulservice.controller;

import com.example.myrestfulservice.beans.User;
import com.example.myrestfulservice.exception.UserNotFoundException;
import com.example.myrestfulservice.service.UserDaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class Usercontroller {
    private UserDaoService service;

    public Usercontroller(UserDaoService service) {
        this.service = service;
    }
    @GetMapping
    public List<User> retrieveAllUsers(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public User retrieveUser(@PathVariable(value = "id") int id ){
        //return service.findOne(id);
        User user = service.findOne(id);
        if(user == null){
            throw new UserNotFoundException("id=" + id);
        }
        return user;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User savedUser = service.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable(value = "id") int id ){

        User user = service.deleteById(id);
        if(user == null){
            throw new UserNotFoundException("id=" + id);
        }

    }


    @PutMapping("/{id}")
    public ResponseEntity<User> modifyUserById(@PathVariable(value = "id") int id,
                               @RequestBody User user){
        user.setId(id);
        User modifiedUser = service.updateUserById(user);
        if(modifiedUser == null){
            throw new UserNotFoundException("id=" + id);
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .build().toUri();
        return ResponseEntity.created(location).build();
    }
}
