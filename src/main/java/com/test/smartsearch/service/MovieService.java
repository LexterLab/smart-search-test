package com.test.smartsearch.service;

import com.test.smartsearch.mapper.MovieMapper;
import com.test.smartsearch.model.Movie;
import com.test.smartsearch.model.SearchKeyWord;
import com.test.smartsearch.payload.searchmovies.SearchMovieInfo;
import com.test.smartsearch.payload.searchmovies.SearchMovieResponse;
import com.test.smartsearch.repository.MovieRepository;
import com.test.smartsearch.repository.SearchKeywordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieService {
    private final MovieRepository movieRepository;
    private final SearchKeywordRepository searchKeywordRepository;

    public SearchMovieResponse searchMovies(String query, int pageNo, int pageSize, String sortBy, String sortDir) {
        log.info("Searching movie by query: {}", query);

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable moviePages = PageRequest.of(pageNo, pageSize, sort);

        Page<Movie> movies = searchMovies(query, pageNo, pageSize, moviePages);
        log.info("Found {} movies", movies.getContent());

        List<SearchMovieInfo> movieInfos = MovieMapper.INSTANCE.moviesToSearchMovieInfos(movies.getContent());
        log.info("Movie infos: {}", movieInfos);

        SearchMovieResponse response = MovieMapper.INSTANCE.moviePagesToSearchMovieResponse(movies, movieInfos);

        log.info("Search movie response: {}", response);

        return response;
    }

    private Page<Movie> searchMovies(String query, int pageNo, int pageSize, Pageable moviePages) {
        Optional<SearchKeyWord> keyword = searchKeyWord(query);

        if (keyword.isPresent()) {
            log.info("Keyword found: {}", keyword.get().getKeyword());

            List<Movie> moviesList = keyword.get().getMovies();

            int start = Math.min(pageNo * pageSize, moviesList.size());
            int end = Math.min((pageNo + 1) * pageSize, moviesList.size());
            List<Movie> moviesPageContent = moviesList.subList(start, end);

            return new PageImpl<>(moviesPageContent, moviePages, moviesList.size());
        } else {
            log.info("No keyword found for query: {}", query);

            Page<Movie> movies = movieRepository.search(query, moviePages);
            SearchKeyWord newKeyword =  SearchKeyWord
                    .builder()
                    .keyword(query)
                    .movies(movies.getContent())
                    .build();

            cacheSearchResults(newKeyword);

            return movies;
        }
    }

    private Optional<SearchKeyWord> searchKeyWord(String query) {
        return searchKeywordRepository.findById(query);
    }

    @Async
    public void cacheSearchResults(SearchKeyWord searchKeyWord) {
        searchKeywordRepository.save(searchKeyWord);
    }
}
