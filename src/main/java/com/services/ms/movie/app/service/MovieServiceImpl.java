package com.services.ms.movie.app.service;

import com.services.ms.movie.app.mapper.MovieMapper;
import com.services.ms.movie.app.models.dto.CreateMovieRequest;
import com.services.ms.movie.app.models.dto.MovieResponse;
import com.services.ms.movie.app.models.entity.Movie;
import com.services.ms.movie.app.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

  private final MovieRepository repository;
  private final MovieMapper mapper;

  @Override
  public Mono<MovieResponse> findById(Long id) {
    return repository.findById(id)
        .map(mapper::toMovieResponse);
  }

  @Override
  public Flux<MovieResponse> findAll() {
    return repository.findAll()
        .map(mapper::toMovieResponse);
  }

  @Override
  public Flux<MovieResponse> findByNameContaining(String name) {
    return repository.findByNameContainingIgnoreCase(name)
        .map(mapper::toMovieResponse);
  }

  @Override
  public Flux<MovieResponse> findByGenre(String genre) {
    return repository.findByGenreIgnoreCase(genre)
        .map(mapper::toMovieResponse);
  }

  @Override
  public Mono<MovieResponse> save(CreateMovieRequest request) {
    return repository.save(mapper.toMovie(request))
        .map(mapper::toMovieResponse);
  }

  @Override
  public Mono<MovieResponse> update(Long id, CreateMovieRequest request) {
    return repository.findById(id)
        .flatMap(savedMovie -> {
          Movie movieToUpdate = mapper.toMovie(request);
          movieToUpdate.setId(savedMovie.getId());
          return repository.save(movieToUpdate);
        })
        .map(mapper::toMovieResponse);
  }

  @Override
  public Mono<Void> delete(Long id) {
    return repository.deleteById(id);
  }
}
