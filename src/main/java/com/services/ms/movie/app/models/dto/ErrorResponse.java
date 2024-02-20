package com.services.ms.movie.app.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class ErrorResponse {

  private String code;
  private HttpStatus status;
  private String message;
  private List<String> detailMessages;

  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private LocalDateTime timeStamp;

}
