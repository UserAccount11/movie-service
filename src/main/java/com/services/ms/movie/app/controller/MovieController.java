package com.services.ms.movie.app.controller;

import com.services.ms.movie.app.models.dto.CreateMovieRequest;
import com.services.ms.movie.app.models.dto.MovieResponse;
import com.services.ms.movie.app.service.MovieService;
import com.services.ms.movie.app.utils.exceptions.MovieNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/movies")
public class MovieController {

  private final MovieService service;

  @GetMapping("/{id}")
  public Mono<MovieResponse> findById(@PathVariable Long id) {
    return service.findById(id)
        .switchIfEmpty(Mono.error(new MovieNotFoundException()));
  }

  @GetMapping
  public Flux<MovieResponse> findAll(@RequestParam(name = "name", required = false) String name) {
    return Objects.isNull(name)
        ? service.findAll()
        : service.findByNameContaining(name);
  }

  @GetMapping("/genre")
  public Flux<MovieResponse> findByGenre(
      @RequestParam(name = "genre") String genre) {
    return service.findByGenre(genre);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<MovieResponse> save(@RequestBody @Valid Mono<CreateMovieRequest> request) {
    return request.flatMap(service::save);
  }


  @PutMapping("/{id}")
  public Mono<MovieResponse> update(
      @PathVariable Long id, @RequestBody @Valid CreateMovieRequest request) {
    return service.update(id, request)
        .switchIfEmpty(Mono.error(new MovieNotFoundException()));
  }

  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<Void>> delete(@PathVariable Long id) {
    return service.findById(id)
        .flatMap(movie -> service.delete(id)
            .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT))))
          .switchIfEmpty(Mono.error(new MovieNotFoundException()));
  }

}
