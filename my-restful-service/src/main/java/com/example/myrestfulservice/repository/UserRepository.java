package com.example.myrestfulservice.repository;

import com.example.myrestfulservice.beans.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
//    find -> select * from user where name =?
//    save -> update , insert
//    delete -> delete
//    아래 소스 없어도 사용 가능
//    List<User> findName(String name);
    User findByName(String name);
}
