package com.services.ms.movie.app.handlers;

import com.services.ms.movie.app.models.dto.CreateMovieRequest;
import com.services.ms.movie.app.models.dto.ErrorResponse;
import com.services.ms.movie.app.models.dto.MovieResponse;
import com.services.ms.movie.app.service.MovieService;
import com.services.ms.movie.app.utils.ErrorCatalog;
import com.services.ms.movie.app.utils.exceptions.MovieNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MovieHandler {

  private final MovieService service;
  private final Validator validator;

  public Mono<ServerResponse> findAll(ServerRequest request) {
    Optional<String> nameOptional = request.queryParam("name");
    return nameOptional
        .map(name -> ServerResponse.ok()
            .body(service.findByNameContaining(name), MovieResponse.class))
        .orElseGet(() -> ServerResponse.ok()
            .body(service.findAll(), MovieResponse.class));
  }

  public Mono<ServerResponse> findByGenre(ServerRequest request) {
    String genre = request.queryParam("genre").orElse("");
    return ServerResponse.ok()
        .body(service.findByGenre(genre), MovieResponse.class);
  }
  public Mono<ServerResponse> findById(ServerRequest request) {
    Long id = Long.parseLong(request.pathVariable("id"));
    return service.findById(id)
        .flatMap(movieResponse -> ServerResponse.ok()
            .bodyValue(movieResponse))
        .switchIfEmpty(Mono.error(new MovieNotFoundException()));
  }

  public Mono<ServerResponse> save(ServerRequest request) {
    Mono<CreateMovieRequest> requestMono = request.bodyToMono(CreateMovieRequest.class);
    return requestMono
        .flatMap(createMovieRequest -> {
          Errors errors = new BeanPropertyBindingResult(
              createMovieRequest, CreateMovieRequest.class.getName());
          validator.validate(createMovieRequest, errors);
          if (errors.hasErrors()) {
            return buildServerResponseForBadRequest(errors);
          } else {
            return ServerResponse.status(HttpStatus.CREATED)
                .body(service.save(createMovieRequest), MovieResponse.class);
          }
        });
  }

  public Mono<ServerResponse> update(ServerRequest request) {
    Long id = Long.parseLong(request.pathVariable("id"));
    Mono<CreateMovieRequest> requestMono = request.bodyToMono(CreateMovieRequest.class);
    return requestMono
        .flatMap(createMovieRequest -> {
          Errors errors = new BeanPropertyBindingResult(
              createMovieRequest, CreateMovieRequest.class.getName());
          validator.validate(createMovieRequest, errors);
          if (errors.hasErrors()) {
            return buildServerResponseForBadRequest(errors);
          } else {
            return ServerResponse.status(HttpStatus.CREATED)
                .body(service.update(id, createMovieRequest), MovieResponse.class);
          }
        })
        .switchIfEmpty(Mono.error(new MovieNotFoundException()));
  }

  public Mono<ServerResponse> delete(ServerRequest request) {
    Long id = Long.parseLong(request.pathVariable("id"));
    return service.findById(id)
        .flatMap(movieResponse -> service.delete(id)
            .then(ServerResponse.noContent().build()))
        .switchIfEmpty(Mono.error(new MovieNotFoundException()));
  }

  private Mono<ServerResponse> buildServerResponseForBadRequest(Errors errors) {
    return ServerResponse.status(HttpStatus.BAD_REQUEST)
        .bodyValue(ErrorResponse.builder()
            .code(ErrorCatalog.INVALID_MOVIE_PARAMETERS.getCode())
            .status(HttpStatus.BAD_REQUEST)
            .message(ErrorCatalog.INVALID_MOVIE_PARAMETERS.getMessage())
            .detailMessages(errors.getFieldErrors()
                .stream()
                .map(fieldError -> "The field " + fieldError.getField() + " " + fieldError.getDefaultMessage())
                .collect(Collectors.toList()))
            .timeStamp(LocalDateTime.now())
            .build());
  }

}
