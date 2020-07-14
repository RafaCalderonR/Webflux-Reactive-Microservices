package com.pi.microservice.factory.service;

import com.pi.microservice.factory.dao.FactoryDao;
import com.pi.microservice.factory.document.Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FactoryServiceImpl implements FactoryService {

    @Autowired
    private FactoryDao factoryDao;

    @Override
    public Flux<Factory> findAll() {
        return factoryDao.findAll();
    }

    @Override
    public Flux<Factory> findByLocation(double latitude, double longitude, int distance) {
        GeoJsonPoint point = new GeoJsonPoint(latitude,longitude);
        Distance kilometers = new Distance(distance, Metrics.KILOMETERS);
        return  factoryDao.findByLocationNear(point, kilometers);
    }


    @Override
    public Mono<Factory> findById(String id) {
        return  factoryDao.findById(id);
    }

    @Override
    public Mono<Factory> save(Factory product) {
        return factoryDao.save(product);
    }

    @Override
    public Mono<Void> delete(Factory product) {
        return factoryDao.delete(product);
    }

    @Override
    public Flux<Factory> findBySearch(String search) {
        return factoryDao.findByTitleRegex(search);
    }

    @Override
    public Flux<Factory> findByIdOwner(String idOwner) {
        return  factoryDao.findByIdOwner(idOwner);
    }
}
