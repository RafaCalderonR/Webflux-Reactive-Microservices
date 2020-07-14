package com.pi.microservice.farming.service;

import com.pi.microservice.farming.document.Farming;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FarmingService {

    public Flux<Farming> findAll();

    public Flux<Farming> findByLocation(double latitude, double longitude, int distance);

    public Mono<Farming> findById(String id);

    public Mono<Farming> save(Farming product);

    public Mono<Void> delete(Farming product);

    public Flux<Farming> findBySearch(String search);

    public Flux<Farming> findByIdOwner(String idOwner);
}