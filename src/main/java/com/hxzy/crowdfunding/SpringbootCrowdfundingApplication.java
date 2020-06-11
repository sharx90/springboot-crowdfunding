package com.hxzy.crowdfunding;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan(basePackages = "com.hxzy.crowdfunding.mapper")
@EnableTransactionManagement
public class SpringbootCrowdfundingApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpringbootCrowdfundingApplication.class, args);

    }

}
