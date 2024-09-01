package com.test.smartsearch.service;

import com.test.smartsearch.model.Movie;
import com.test.smartsearch.model.SearchKeyWord;
import com.test.smartsearch.repository.SearchKeywordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MovieUpdateListener {
    private final SearchKeywordRepository searchKeywordRepository;

    @KafkaListener(topics = "movie_updates", groupId = "movie_group")
    public void handleMovieUpdate(Movie movie) {
        log.info("Received update for movie: {}", movie);

        Iterable<SearchKeyWord> keywords = searchKeywordRepository.findAll();

        for (SearchKeyWord keyword : keywords) {

            Optional<Movie> movieToUpdateCache = keyword.getMovies().stream()
                    .filter(m -> m.getId().equals(movie.getId()))
                    .findFirst();

            if (movieToUpdateCache.isPresent()) {
                log.info("Updating cache for keyword: {}", keyword.getKeyword());

                int movieToUpdateIndex = keyword.getMovies().indexOf(movieToUpdateCache.get());
                keyword.getMovies().set(movieToUpdateIndex, movie);

                searchKeywordRepository.save(keyword);

                log.info("Cache updated for keyword: {}", keyword.getKeyword());
            }
        }
    }
}

