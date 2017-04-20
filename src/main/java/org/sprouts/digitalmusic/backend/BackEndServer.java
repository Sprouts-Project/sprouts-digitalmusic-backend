package org.sprouts.digitalmusic.backend;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableEurekaClient
@EntityScan(basePackages = {"org.sprouts.digitalmusic.model","org.sprouts.digitalmusic.pojo"})
public class BackEndServer implements ApplicationRunner {

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "backend-server");
		SpringApplication.run(BackEndServer.class, args);
	}

	public void run(ApplicationArguments arg0) throws Exception {
	}
	
	@Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)          
          .select()
          .apis(RequestHandlerSelectors.basePackage("org.sprouts.digitalmusic.backend.controller"))
          .build();
    }
	
	//@Bean
	//public KieContainer kieContainer() {
	//	return KieServices.Factory.get().getKieClasspathContainer();
	//}

}