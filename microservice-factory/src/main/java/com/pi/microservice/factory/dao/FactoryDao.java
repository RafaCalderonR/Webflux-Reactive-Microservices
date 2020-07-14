package com.pi.microservice.factory.dao;

import com.pi.microservice.factory.document.Factory;
import org.springframework.data.geo.Distance;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface FactoryDao extends ReactiveMongoRepository<Factory, String> {
    Flux<Factory> findByLocationNear(GeoJsonPoint location, Distance distance);
    Flux<Factory> findByIdOwner(String idOwner);
    Flux<Factory> findByTitleRegex(String search);
}
