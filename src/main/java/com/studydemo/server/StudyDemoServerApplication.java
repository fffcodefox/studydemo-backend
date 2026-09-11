package com.studydemo.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类。
 */
@SpringBootApplication
@MapperScan("com.studydemo.server.mapper")
public class StudyDemoServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyDemoServerApplication.class, args);
    }
}
