package com.it.elderhub;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@SpringBootApplication
@EnableScheduling
@MapperScan("com.it.elderhub.mapper")
public class ElderHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElderHubApplication.class, args);
        log.info("颐养中心系统启动成功");
    }

}
