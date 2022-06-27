package com.example.myrestfulservice.controller;


import com.example.myrestfulservice.beans.HelloWorldBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@RestController
@RequestMapping(value = "/")
public class HelloWorldController {

    private MessageSource messageSource;
    private LocaleResolver localeResolver;

    @Autowired
    public HelloWorldController(MessageSource messageSource) {
        this.messageSource = messageSource;
        this.localeResolver = localeResolver;
    }

    //http://loalhost:8080/a/hello-world
//    @RequestMapping(value = "/hello-world", method = RequestMethod.GET)
    @GetMapping(value = "/hello-world")
    public String sayHello(){
        return "Hello, World!";
    }

    @GetMapping(value = "/hello-world-internationalized")
    public String sayHelloByLocale(@RequestHeader(name="Accept-Language", required = false) Locale locale,
                                   HttpServletRequest request){
        if (locale==null){
            locale = localeResolver.resolveLocale(request);
        }
        return messageSource.getMessage("greeting.message", null, locale);
    }

//    @GetMapping(value = "/hello-world-bean")
//    public HelloWorldBean sayHelloWithBean(){
//        return new HelloWorldBean("Hi, there!");
//    }
    @GetMapping(value = "/hello-world-bean/path-variable/{name}")
//    public HelloWorldBean sayHelloUsingPathVariable(String name){
    public HelloWorldBean sayHelloUsingPathVariable(@PathVariable(value = "name") String name){
        return new HelloWorldBean(String.format("Hi, %s", name));
    }
}
