package com.pi.microservice.farming;


import com.pi.microservice.farming.controller.FarmingController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;




@Configuration
public class RouterFunctionConfig {

    @Bean
    public RouterFunction<ServerResponse> routes(FarmingController controller){
        return route(GET("/list"), request-> controller.list(request))
                .andRoute(GET("/listByDistance/{latitude}/{longitude}/{distance}"), request->controller.listByDistance(request))
                .andRoute(GET("/detail/{id}"), request-> controller.detail(request))
                .andRoute(POST("/add"), request -> controller.add(request))
                .andRoute(PUT("/update/{id}"), request -> controller.edit(request))
                .andRoute(DELETE("/delete/{id}"),request-> controller.delete(request))
                .andRoute(GET("/listBySearch/{search}"),request-> controller.listByTextSearch(request))
                .andRoute(GET("/listByOwner/{idOwner}"),request-> controller.listByIdOwner(request));
    }

    @Bean
    public RouterFunction<ServerResponse> staticResourceLocator(){
        return RouterFunctions.resources("/images/**", new ClassPathResource("images/"));
    }


}