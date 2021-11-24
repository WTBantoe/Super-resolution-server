package com.sr;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author cyh
 * @Date 2021/9/27 15:49
 */

@SpringBootApplication
@EnableSwagger2
@ServletComponentScan("com.sr")
@EnableTransactionManagement
@MapperScan("com.sr.dao")
public class SrApplication {
    public static void main(String[] args) {
        SpringApplication.run(SrApplication.class, args);
    }
}
