package com.services.ms.movie.app.service;

import com.services.ms.movie.app.models.dto.CreateMovieRequest;
import com.services.ms.movie.app.models.dto.MovieResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovieService {

  Mono<MovieResponse> findById(Long id);
  Flux<MovieResponse> findAll();
  Flux<MovieResponse> findByNameContaining(String name);
  Flux<MovieResponse> findByGenre(String genre);
  Mono<MovieResponse> save(CreateMovieRequest request);
  Mono<MovieResponse> update(Long id, CreateMovieRequest request);
  Mono<Void> delete(Long id);

}
