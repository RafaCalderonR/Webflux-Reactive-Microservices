package com.pi.microservice.factory.controller;

import java.io.File;
import java.net.URI;
import java.util.Date;
import java.util.UUID;
import com.pi.microservice.factory.document.Factory;
import com.pi.microservice.factory.service.FactoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.FormFieldPart;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import static org.springframework.web.reactive.function.BodyInserters.*;
import org.springframework.beans.factory.annotation.Value;
@Component
public class FactoryController {


    @Autowired
    private FactoryService factoryService;

    @Value("/home/rafa/Escritorio/Proyecto Integrado/microservice-factory/src/main/resources/images/")
    private String path;

    public Mono<ServerResponse> list(ServerRequest request){
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(factoryService.findAll(), Factory.class);
    }

    public Mono<ServerResponse> detail(ServerRequest request){
        return factoryService.findById(request.pathVariable("id"))
                .flatMap(factory -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(factory)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> listByDistance(ServerRequest request){

        return  ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(factoryService.findByLocation(Double.parseDouble(request.pathVariable("latitude")),
                        Double.parseDouble(request.pathVariable("longitude")),
                        Integer.parseInt(request.pathVariable("distance"))),
                        Factory.class);

    }

    public Mono<ServerResponse> edit(ServerRequest request){
        Mono<Factory> factory = request.bodyToMono(Factory.class);
        String id = request.pathVariable("id");

        Mono<Factory> factoryDb = factoryService.findById(id);

        return factoryDb.zipWith(factory,(db, req)->{
            db.setTitle(req.getTitle());
            return db;
        }).flatMap(data -> ServerResponse.created(URI.create("/api/factorys/".concat(data.getId())))
                .contentType(MediaType.APPLICATION_JSON)
                .body(factoryService.save(data), Factory.class))
                .switchIfEmpty(ServerResponse.notFound().build());

    }

    public Mono<ServerResponse> add(ServerRequest request) {

        Mono<Factory> product = request.multipartData().map(multipart -> {

            FormFieldPart idOwner = (FormFieldPart) multipart.toSingleValueMap().get("idOwner");
            FormFieldPart description = (FormFieldPart) multipart.toSingleValueMap().get("description");
            FormFieldPart title = (FormFieldPart) multipart.toSingleValueMap().get("title");
            FormFieldPart latitude = (FormFieldPart) multipart.toSingleValueMap().get("latitude");
            FormFieldPart longitude = (FormFieldPart) multipart.toSingleValueMap().get("longitude");
            FormFieldPart price = (FormFieldPart) multipart.toSingleValueMap().get("price");


            return new Factory(
                    idOwner.value(),
                    description.value(),
                    title.value(),
                    Double.parseDouble(latitude.value()),
                    Double.parseDouble(longitude.value()),
                    Double.parseDouble(price.value())
                    /*measure.value(),
                    Integer.parseInt(quantity.value())*/
            );
        });

        return request.multipartData().map(multipart -> multipart.toSingleValueMap().get("image"))
                .cast(FilePart.class)
                .flatMap(file -> product
                        .flatMap(p -> {
                            p.setImage(UUID.randomUUID().toString() + "-" + file.filename()
                                    .replace(" ", "-")
                                    .replace(":", "")
                                    .replace("\\", ""));

                            p.setCreateAt(new Date());
                            return file.transferTo(new File(path + p.getImage())).then(factoryService.save(p));
                        })).flatMap(p -> ServerResponse.created(URI.create("/api/factory/".concat(p.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(p)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }




    public Mono<ServerResponse> listByTextSearch(ServerRequest request){
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(factoryService.findBySearch(request.pathVariable("search")),
                        Factory.class);
    }

    public Mono<ServerResponse> listByIdOwner(ServerRequest request){
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(factoryService.findByIdOwner(request.pathVariable("idOwner")),
                        Factory.class);
    }



    public Mono<ServerResponse> delete(ServerRequest request){
        String id = request.pathVariable("id");

        Mono<Factory> factoryDb = factoryService.findById(id);

        return factoryDb.flatMap(product-> factoryService.delete(product).then(ServerResponse.noContent().build()))
                .switchIfEmpty(ServerResponse.notFound().build());

    }
}
