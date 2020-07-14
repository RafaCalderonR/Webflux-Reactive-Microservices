package com.pi.microservice.cloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@EnableEurekaClient
@SpringBootApplication
public class MicroserviceCloudGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceCloudGatewayApplication.class, args);
    }

}
