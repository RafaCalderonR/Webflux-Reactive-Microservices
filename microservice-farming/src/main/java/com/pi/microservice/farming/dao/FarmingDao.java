package com.pi.microservice.farming.dao;


import com.pi.microservice.farming.document.Farming;
import org.springframework.data.geo.Distance;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface FarmingDao extends ReactiveMongoRepository<Farming, String> {


    Flux<Farming> findByLocationNear(GeoJsonPoint location, Distance distance);
    Flux<Farming> findByIdOwner(String idOwner);
    Flux<Farming> findByTitleRegex(String search);
    Flux<Farming> findByTitle(String search);

}