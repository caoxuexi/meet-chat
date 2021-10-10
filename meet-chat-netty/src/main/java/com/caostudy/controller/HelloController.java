package com.caostudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Cao Study
 * @description <h1>HelloController</h1>
 * @date 2021-10-10 15:47
 */
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello(){
        return "hello muxin~~";
    }
}
