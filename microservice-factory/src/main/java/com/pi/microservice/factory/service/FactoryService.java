package com.pi.microservice.factory.service;

import com.pi.microservice.factory.document.Factory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FactoryService {


    public Flux<Factory> findAll();
    public Mono<Factory> findById(String id);
    public Mono<Factory> save(Factory factory);
    public Mono<Void> delete(Factory factory);
    public Flux<Factory> findByLocation(double latitude, double longitude, int distance);
    public Flux<Factory> findBySearch(String search);
    public Flux<Factory> findByIdOwner(String idOwner);

}
