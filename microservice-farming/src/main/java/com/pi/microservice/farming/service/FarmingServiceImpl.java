package com.pi.microservice.farming.service;


import com.mongodb.client.model.geojson.Position;

import com.pi.microservice.farming.MicroserviceFarmingApplication;
import com.pi.microservice.farming.dao.FarmingDao;
import com.pi.microservice.farming.document.Farming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FarmingServiceImpl implements FarmingService {

    private static final Logger log = LoggerFactory.getLogger(MicroserviceFarmingApplication.class);

    @Autowired
    private FarmingDao farmingDao;

    @Override
    public Flux<Farming> findAll() {
        return farmingDao.findAll();
    }

    @Override
    public Flux<Farming> findByLocation(double latitude, double longitude, int distance) {
        GeoJsonPoint point = new GeoJsonPoint(latitude,longitude);
        Distance kilometers = new Distance(distance, Metrics.KILOMETERS);
        return  farmingDao.findByLocationNear(point, kilometers);
    }

    @Override
    public Mono<Farming> findById(String id) {
        return farmingDao.findById(id);
    }

    @Override
    public Mono<Farming> save(Farming product) {
        return farmingDao.save(product);
    }

    @Override
    public Mono<Void> delete(Farming product) {
        return farmingDao.delete(product);
    }

    @Override
    public Flux<Farming> findBySearch(String search) {
        return farmingDao.findByTitleRegex(search);
    }

    @Override
    public Flux<Farming> findByIdOwner(String idOwner) {
        return  farmingDao.findByIdOwner(idOwner);
    }
}