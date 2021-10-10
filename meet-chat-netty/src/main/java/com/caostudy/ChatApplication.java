package com.caostudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author Cao Study
 * @description <h1>chatApplication</h1>
 * @date 2021-10-10 15:25
 */
@SpringBootApplication
@MapperScan(basePackages = "com.caostudy.mapper")
@ComponentScan(basePackages = {"com.caostudy","org.n3r.idworker"})
public class ChatApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class,args);
    }
}
