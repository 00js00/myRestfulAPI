package com.example.myrestfulservice.controller;

import com.example.myrestfulservice.beans.User;
import com.example.myrestfulservice.exception.UserNotFoundException;
import com.example.myrestfulservice.service.UserDaoService;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

//method import
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class Usercontroller {
    private UserDaoService service;

    public Usercontroller(UserDaoService service) {
        this.service = service;
    }


    @GetMapping
    @ApiOperation(value = "all user list search", notes = "search all user list")
//    public List<User> retrieveAllUsers(){
//        return service.findAll();
//    }
    public ResponseEntity<CollectionModel<EntityModel<User>>> retrieveAllUsers(){
        List<User> users = service.findAll();
        List<EntityModel<User>> result = new ArrayList<>();

        for (User user: users){

            EntityModel entityModel = EntityModel.of(user);
            entityModel.add(linkTo(methodOn(this.getClass()).retrieveUser(user.getId())).withRel("detail"));
            result.add(entityModel);
        }
        return ResponseEntity.ok(CollectionModel.of(result,linkTo(methodOn(this.getClass()).retrieveAllUsers()).withSelfRel()));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "detail user list search", notes = "search detail user list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "user id", required = true, paramType = "path")
    })
//    public User retrieveUser(@PathVariable(value = "id") int id ){
//        //return service.findOne(id);
//        User user = service.findOne(id);
//        if(user == null){
//            throw new UserNotFoundException("id=" + id);
//        }
//        return user;
//    }

//    hateoas
    public ResponseEntity retrieveUser(@PathVariable(value = "id") int id ){
        //return service.findOne(id);
        User user = service.findOne(id);
        if(user == null){
            throw new UserNotFoundException("id=" + id);
        }
        EntityModel entityModel = EntityModel.of(user);

//        staic import methods X
//        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(linkTo.withRel("all-users"));
//        link to all user

        return ResponseEntity.ok(entityModel);
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
