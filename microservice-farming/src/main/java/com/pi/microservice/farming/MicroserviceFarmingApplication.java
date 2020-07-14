package com.pi.microservice.farming;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@EnableEurekaClient
@SpringBootApplication
public class MicroserviceFarmingApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceFarmingApplication.class, args);
    }

}



