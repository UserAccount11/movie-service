package com.services.ms.movie.app;

import com.services.ms.movie.app.models.entity.Movie;
import com.services.ms.movie.app.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class MovieServiceApplication implements CommandLineRunner {

	private final MovieRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(MovieServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Flux<Movie> productFlux = Flux.just(
						new Movie(null, "Avenger: Infinity Wars", "Action", LocalDate.now()),
						new Movie(null, "Gladiator", "Drama", LocalDate.now()),
						new Movie(null, "Black Panther", "Action", LocalDate.now()))
				.flatMap(repository::save);

		productFlux
				.thenMany(repository.findAll())
				.subscribe(movie -> log.info(String.format("Movie inserted with ID: %s and name: %s", movie.getId(), movie.getName())));
	}

}
