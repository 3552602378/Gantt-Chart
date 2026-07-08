package com.gantt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.gantt.modules.*.mapper")
public class GanttApplication {

    public static void main(String[] args) {
        SpringApplication.run(GanttApplication.class, args);
    }
}
