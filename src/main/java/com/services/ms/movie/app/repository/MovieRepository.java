package com.services.ms.movie.app.repository;

import com.services.ms.movie.app.models.entity.Movie;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface MovieRepository extends ReactiveCrudRepository<Movie, Long> {

  Flux<Movie> findByNameContainingIgnoreCase(String name);

  Flux<Movie> findByGenreIgnoreCase(String genre);

}
