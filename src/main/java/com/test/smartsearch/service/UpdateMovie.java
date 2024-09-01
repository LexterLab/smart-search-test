package com.test.smartsearch.service;

import com.test.smartsearch.exception.ResourceNotFoundException;
import com.test.smartsearch.mapper.MovieMapper;
import com.test.smartsearch.model.Movie;
import com.test.smartsearch.payload.updatemovie.UpdateMovieRequest;
import com.test.smartsearch.payload.updatemovie.UpdateMovieResponse;
import com.test.smartsearch.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpdateMovie {
    private final MovieRepository movieRepository;
    private final KafkaTemplate<String, Movie> kafkaTemplate;

    @Transactional
    public UpdateMovieResponse updateMovie(UpdateMovieRequest request) {
        log.info("Start updateMovie {}", request);
        Movie movie = movieRepository.findById(UUID.fromString(request.movieId()))
                .orElseThrow(() -> new ResourceNotFoundException("Movie", "id", request.movieId()));

        MovieMapper.INSTANCE.updateMovie(movie, request);

        kafkaTemplate.send("movie_updates", movie);

        movieRepository.save(movie);

        UpdateMovieResponse response = UpdateMovieResponse.builder()
                .movieId(movie.getId())
                .build();

        log.info("End updateMovie {}", response);
        return response;
    }
}
