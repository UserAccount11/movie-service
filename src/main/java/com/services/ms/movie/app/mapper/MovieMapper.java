package com.services.ms.movie.app.mapper;

import com.services.ms.movie.app.models.dto.CreateMovieRequest;
import com.services.ms.movie.app.models.dto.MovieResponse;
import com.services.ms.movie.app.models.entity.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MovieMapper {

  MovieResponse toMovieResponse(Movie movie);

  Movie toMovie(CreateMovieRequest request);

}
