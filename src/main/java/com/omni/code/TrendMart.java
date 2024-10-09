package com.omni.code;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.omni.code.mapper")  // Specify the package of your mappers
public class TrendMart {

	public static void main(String[] args) {
		SpringApplication.run(TrendMart.class, args);
	}

}
