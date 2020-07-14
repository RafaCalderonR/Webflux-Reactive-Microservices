package com.pi.microservice.farming.controller;



import com.pi.microservice.farming.MicroserviceFarmingApplication;
import com.pi.microservice.farming.document.Farming;
import com.pi.microservice.farming.service.FarmingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.FormFieldPart;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import java.io.File;
import java.net.URI;
import java.util.Date;
import java.util.UUID;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;


@Component
public class FarmingController {

    @Autowired
    private FarmingService service;


    @Value("/home/rafa/Escritorio/Proyecto Integrado/microservice-farming/src/main/resources/images/")
    private String path;

    private static final Logger log = LoggerFactory.getLogger(MicroserviceFarmingApplication.class);


    public Mono<ServerResponse> list(ServerRequest request){
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(service.findAll(), Farming.class);
    }

    public Mono<ServerResponse> listByDistance(ServerRequest request){

        return  ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(service.findByLocation(Double.parseDouble(request.pathVariable("latitude")),
                        Double.parseDouble(request.pathVariable("longitude")),
                        Integer.parseInt(request.pathVariable("distance"))),
                        Farming.class);

    }

    public Mono<ServerResponse> listByTextSearch(ServerRequest request){
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(service.findBySearch(request.pathVariable("search")),
                        Farming.class);
    }

    public Mono<ServerResponse> listByIdOwner(ServerRequest request){
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(service.findByIdOwner(request.pathVariable("idOwner")),
                        Farming.class);
    }


    public Mono<ServerResponse> detail(ServerRequest request){
        return service.findById(request.pathVariable("id"))
                .flatMap(product -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(product)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }



    public Mono<ServerResponse> edit(ServerRequest request){
        Mono<Farming> producto = request.bodyToMono(Farming.class);
        String id = request.pathVariable("id");

        Mono<Farming> productoDb = service.findById(id);

        return productoDb.zipWith(producto, (db, req) ->{
            db.setTitle(req.getTitle());
            return db;

        }).flatMap(product -> ServerResponse.created(URI.create("/api/v2/products/".concat(product.getId())))
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.save(product), Farming.class))
                .switchIfEmpty(ServerResponse.notFound().build());

    }

    public Mono<ServerResponse> add(ServerRequest request){

        Mono<Farming> product = request.multipartData().map(multipart ->{

            FormFieldPart idOwner = (FormFieldPart) multipart.toSingleValueMap().get("idOwner");
            FormFieldPart description = (FormFieldPart) multipart.toSingleValueMap().get("description");
            FormFieldPart title = (FormFieldPart) multipart.toSingleValueMap().get("title");
            FormFieldPart latitude = (FormFieldPart) multipart.toSingleValueMap().get("latitude");
            FormFieldPart longitude = (FormFieldPart) multipart.toSingleValueMap().get("longitude");
            FormFieldPart price = (FormFieldPart) multipart.toSingleValueMap().get("price");
           /* FormFieldPart measure = (FormFieldPart) multipart.toSingleValueMap().get("measure");
            FormFieldPart quantity = (FormFieldPart) multipart.toSingleValueMap().get("quantity");*/

            return new Farming(
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
                            return file.transferTo(new File(path + p.getImage())).then(service.save(p));
                        })).flatMap(p -> ServerResponse.created(URI.create("/api/farming/".concat(p.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(p)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }



    public Mono<ServerResponse> delete(ServerRequest request){
        String id = request.pathVariable("id");

        Mono<Farming> productoDb = service.findById(id);

        return productoDb.flatMap(product-> service.delete(product).then(ServerResponse.noContent().build()))
                .switchIfEmpty(ServerResponse.notFound().build());

    }
}