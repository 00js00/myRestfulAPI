package com.example.myrestfulservice.controller;

import com.example.myrestfulservice.beans.AdminUser;
import com.example.myrestfulservice.beans.AdminUserV2;
import com.example.myrestfulservice.beans.User;
import com.example.myrestfulservice.exception.UserNotFoundException;
import com.example.myrestfulservice.service.UserDaoService;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
//@RequestMapping("/admin/users")
@RequestMapping("/")
public class AdminUsercontroller {
    private UserDaoService service;

    public AdminUsercontroller(UserDaoService service) {
        this.service = service;
    }
    @GetMapping("/v1/admin/users")
    public MappingJacksonValue retrieveAllUsers4Admin() {
        List<User> users = service.findAll();
        List<AdminUser> adminUsers = new ArrayList<>();
        for (User user : users) {
            AdminUser adminUser = AdminUser.builder()
                    .id(user.getId()).name(user.getName())
                    .password(user.getPassword()).joinDate(user.getJoinDate())
                    .ssn(user.getSsn())
                    .build();

            adminUsers.add(adminUser);
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(adminUsers);
        mapping.setFilters(filters);

        return mapping;
    }
//    @GetMapping
//    public MappingJacksonValue retrieveAllUsers4Admin(){
//        List<User> users = service.findAll();
//        List<AdminUser> adminUsers = new ArrayList<>();
//        for (User user: users) {
//            AdminUser adminUser = AdminUser.builder()
//                    .id(user.getId()).name(user.getName())
//                    .password(user.getPassword()).joinDate(user.getJoinDate())
//                    .ssn(user.getSsn())
//                    .build();
//        }
//        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
//                .filterOutAllExcept("id","name","joinDate");
//        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo",filter);
//        MappingJacksonValue mapping = new MappingJacksonValue(adminUsers);
//
//        mapping.setFilters(filters);
//
//        return mapping;
//    }

//    @GetMapping("/v1/admin/users/{id}")
//    @GetMapping(value="/admin/users/{id}", params="version=1")
//    @GetMapping(value="/admin/users/{id}", headers="X-API-VERSION=1")
    @GetMapping(value="/admin/users/{id}", produces="application/vnd.company.appv1+json")
    public MappingJacksonValue retrieveUser4Admin(@PathVariable(value = "id") int id) {
        User user = service.findOne(id);
        if (user == null) {
            throw new UserNotFoundException("id-" + id);
        }

        AdminUser adminUser = AdminUser.builder()
                .id(user.getId()).name(user.getName())
                .password(user.getPassword()).joinDate(user.getJoinDate())
                .ssn(user.getSsn())
                .build();

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "ssn", "joinDate");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filters);

        return mapping;
    }


//    @GetMapping("/{id}")
//    public MappingJacksonValue retrieveUser4Admin(@PathVariable(value = "id") int id ){
//        //return service.findOne(id);
//        User user = service.findOne(id);
//        if(user == null){
//            throw new UserNotFoundException("id=" + id);
//        }
//
//        AdminUser adminUser = AdminUser.builder()
//                .id(user.getId()).name(user.getName())
//                .password(user.getPassword()).joinDate(user.getJoinDate())
//                .ssn(user.getSsn())
//                .build();
////        user to Admin
//
//        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
//                .filterOutAllExcept("id","name","joinDate","ssn");
//        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo",filter);
//        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
//
//        return mapping;
//    }
//    @GetMapping("/v2/admin/users/{id}")
//    @GetMapping(value="/admin/users/{id}", params="version=2")
//    @GetMapping(value="/admin/users/{id}", headers="X-API-VERSION=2")
    @GetMapping(value="/admin/users/{id}", produces="application/vnd.company.appv2+json")
    public MappingJacksonValue retrieveUser4AdminVer2(@PathVariable(value = "id") int id) {
        User user = service.findOne(id);
        if (user == null) {
            throw new UserNotFoundException("id-" + id);
        }

        AdminUserV2 adminUser = new AdminUserV2();
        BeanUtils.copyProperties(user,adminUser);
        adminUser.setGrade("VIP");

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "ssn", "joinDate","grade");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filters);

        return mapping;
    }

}
