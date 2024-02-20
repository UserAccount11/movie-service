package com.services.ms.movie.app.models.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class CreateMovieRequest {

  @NotEmpty(message = "The field name cannot be empty or null.")
  private String name;

  @NotEmpty(message = "The field genre cannot be empty or null.")
  private String genre;

  @NotNull(message = "The field release_date cannot be empty or null.")
  private LocalDate releaseDate;

}
