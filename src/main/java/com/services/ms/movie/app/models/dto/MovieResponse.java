package com.services.ms.movie.app.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class MovieResponse {

  private Long id;
  private String name;
  private String genre;

  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private LocalDate releaseDate;

}
