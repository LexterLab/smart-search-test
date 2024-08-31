package com.test.smartsearch.service;

import com.test.smartsearch.mapper.MovieMapper;
import com.test.smartsearch.model.Movie;
import com.test.smartsearch.payload.searchmovies.SearchMovieInfo;
import com.test.smartsearch.payload.searchmovies.SearchMovieResponse;
import com.test.smartsearch.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieService {
    private final MovieRepository movieRepository;

    public SearchMovieResponse searchMovies(String query, int pageNo, int pageSize, String sortBy, String sortDir) {
        log.info("Searching movie by query: {}", query);

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable moviePages = PageRequest.of(pageNo, pageSize, sort);

        Page<Movie> movies = movieRepository.search(query, moviePages);

        List<SearchMovieInfo> movieInfos = MovieMapper.INSTANCE.moviesToSearchMovieInfos(movies.getContent());

        SearchMovieResponse response = MovieMapper.INSTANCE.moviePagesToSearchMovieResponse(movies, movieInfos);

        log.info("Search movie response: {}", response);

        return response;
    }
}
