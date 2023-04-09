package com.mahmoud.picturepub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication
public class PicturepubApplication {

	public static void main(String[] args) {
		SpringApplication.run(PicturepubApplication.class, args);
	}


}
