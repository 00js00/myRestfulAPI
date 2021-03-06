package com.example.myrestfulservice.service;

import com.example.myrestfulservice.beans.Post;
import com.example.myrestfulservice.beans.User;
import com.example.myrestfulservice.repository.PostRepository;
import com.example.myrestfulservice.repository.UserRepository;
import com.example.myrestfulservice.vo.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserJpaService {
    private UserRepository userRepository;
    private PostRepository postRepository;

    @Autowired
    public UserJpaService(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public List<User> getAllUsers(){
        List<User> users = userRepository.findAll();

//        람다 표현식
//        users.stream().forEach(v -> {
//            log.info("User -> " + v.getId() + "/" + v.getName());
//        });

        return users;


    }


    public User getUserById(int id){
        Optional<User> user = userRepository.findById(id);

        return user == null ? null : user.get();

    }

    public User getUserByName(String name){
        User user = userRepository.findByName(name);

        return user;
    }

    public void deleteUserById(int id){
        userRepository.deleteById(id);
    }

    public User createUser(User user) {
        user.setJoinDate(new Date());
        User savedUser = userRepository.save(user);

        return savedUser;
    }

    public User updateUserById(int id, User user){ //{name, password}
        Optional<User> _optional = userRepository.findById(id);

        if(!_optional.isPresent()) {
            return null;
        }
        User storedUser = _optional.get();

        user.setId(id);
        user.setSsn(storedUser.getSsn());
        user.setJoinDate(storedUser.getJoinDate()); //user.setJoinDate(new Date());
        userRepository.save(user);// update sql

        return user;
    }
    /*
    Post
    */
    public List<Post> getAllPostsByUser(int id){
        Optional<User> _optional = userRepository.findById(id);

        if(!_optional.isPresent()) {
            return null;
        }
        return _optional.get().getPosts() == null || _optional.get().getPosts().isEmpty()
                ? new ArrayList<>() : _optional.get().getPosts();
    }

    public Post createPost(int id, Post post) {

        Optional<User> _optional = userRepository.findById(id);

        if(!_optional.isPresent()) {
            return null;
        }
        User storedUser = _optional.get();
        post.setUser(storedUser);
        postRepository.save(post);

        return post;
    }

    public Post getPostByPostId(int id){
        Optional<Post> post = postRepository.findById(id);
        return post == null? null : post.get();
    }
}
