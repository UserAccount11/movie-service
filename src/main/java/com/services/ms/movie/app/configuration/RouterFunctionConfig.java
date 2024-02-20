package com.services.ms.movie.app.configuration;

import com.services.ms.movie.app.handlers.MovieHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;

@Configuration
public class RouterFunctionConfig {

  @Bean
  public RouterFunction<ServerResponse> routes(MovieHandler handler) {
    return RouterFunctions
        .route(GET("/api/v2/movies/genre"), handler::findByGenre)
        .andRoute(GET("/api/v2/movies"), handler::findAll)
        .andRoute(GET("/api/v2/movies/{id}"), handler::findById)
        .andRoute(POST("/api/v2/movies"), handler::save)
        .andRoute(PUT("/api/v2/movies/{id}"), handler::update)
        .andRoute(DELETE("/api/v2/movies/{id}"), handler::delete);
  }

}
