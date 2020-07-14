package com.pi.microservice.factory;

import com.pi.microservice.factory.dao.FactoryDao;
import com.pi.microservice.factory.document.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeospatialIndex;
import reactor.core.publisher.Flux;

import java.util.Date;

@EnableEurekaClient
@SpringBootApplication
public class MicroserviceFactoryApplication  {
    public static void main(String[] args) {
        SpringApplication.run(MicroserviceFactoryApplication.class, args);
    }

}
