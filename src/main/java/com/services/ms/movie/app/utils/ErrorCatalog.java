package com.services.ms.movie.app.utils;

import lombok.Getter;

@Getter
public enum ErrorCatalog {

  MOVIE_NOT_FOUND("ERR_MOVIE_001", "Movie not found."),
  INVALID_MOVIE_PARAMETERS("ERR_MOVIE_002", "Invalid movie parameters."),

  GENERIC_ERROR("ERR_GEN_001", "An unexpected error occurred.");

  private final String code;
  private final String message;

  ErrorCatalog(String code, String message) {
    this.code = code;
    this.message = message;
  }

}