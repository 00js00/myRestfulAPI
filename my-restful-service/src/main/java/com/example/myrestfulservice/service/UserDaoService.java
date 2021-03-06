package com.example.myrestfulservice.service;

import com.example.myrestfulservice.beans.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Component
public class UserDaoService {
    private static List<User> users = new ArrayList<>();

    private static int userCount = 3;

    static {
        users.add(new User(1, "Kenneth", new Date(),"pwd1","701010-1111111"));
        users.add(new User(2, "Alice", new Date(),"pwd12","801111-2222222"));
        users.add(new User(3, "Elena", new Date(),"pwd13","901313-1111111"));
    }

    private static int usersCount = 3;

    public List<User> findAll() {
        return users;
    }

    public User save(User user) {
        if (user.getId() == null) {
            user.setId(++usersCount);
        }
        user.setJoinDate(new Date());

        users.add(user);
        return user;
    }

    public User findOne(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public User deleteById(int id) {
        Iterator<User> iterator = users.iterator();

        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getId() == id) {
                iterator.remove();
                return user;

            }
        }
        return null;
    }

    public User updateUserById(User newUser){
        User storedUser = findOne(newUser.getId());
        if (storedUser == null){
            return null;
        } else {
            storedUser.setName(newUser.getName());
            storedUser.setJoinDate(new Date());
            storedUser.setPassword(newUser.getPassword());
            storedUser.setSsn(newUser.getSsn());
            return storedUser;
        }
    }
}