package com.yukiice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@Slf4j  // 日志
@SpringBootApplication
@ServletComponentScan
public class TakeOutServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(TakeOutServerApplication.class, args);
        log.info("项目启动成功");
    }

}

