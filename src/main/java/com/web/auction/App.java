package com.web.auction;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.web.auction.mapper")
public class App {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(App.class, args);
	}

}
