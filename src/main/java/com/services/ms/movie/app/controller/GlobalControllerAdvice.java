package com.services.ms.movie.app.controller;

import com.services.ms.movie.app.models.dto.ErrorResponse;
import com.services.ms.movie.app.utils.ErrorCatalog;
import com.services.ms.movie.app.utils.exceptions.MovieNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalControllerAdvice {

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(MovieNotFoundException.class)
  public Mono<ErrorResponse> handleMovieNotFoundException() {
    return Mono.just(
        ErrorResponse.builder()
            .code(ErrorCatalog.MOVIE_NOT_FOUND.getCode())
            .status(HttpStatus.NOT_FOUND)
            .message(ErrorCatalog.MOVIE_NOT_FOUND.getMessage())
            .timeStamp(LocalDateTime.now())
            .build());
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(WebExchangeBindException.class)
  public Mono<ErrorResponse> handleWebExchangeBindException(WebExchangeBindException exception) {
    return Mono.just(
        ErrorResponse.builder()
            .code(ErrorCatalog.INVALID_MOVIE_PARAMETERS.getCode())
            .status(HttpStatus.BAD_REQUEST)
            .message(ErrorCatalog.INVALID_MOVIE_PARAMETERS.getMessage())
            .detailMessages(exception.getFieldErrors()
                .stream()
                .map(fieldError -> "The field " + fieldError.getField() + " " + fieldError.getDefaultMessage())
                .collect(Collectors.toList()))
            .timeStamp(LocalDateTime.now())
            .build());
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  public Mono<ErrorResponse> handleInternalServeError(
      Exception exception) {
    return Mono.just(
        ErrorResponse.builder()
            .code(ErrorCatalog.GENERIC_ERROR.getCode())
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .message(ErrorCatalog.GENERIC_ERROR.getMessage())
            .detailMessages(Collections.singletonList(exception.getMessage()))
            .timeStamp(LocalDateTime.now())
            .build());
  }

}
