package com.test.smartsearch.service;

import com.test.smartsearch.mapper.MovieMapper;
import com.test.smartsearch.model.Movie;
import com.test.smartsearch.model.SearchKeyWord;
import com.test.smartsearch.payload.searchmovies.SearchMovieInfo;
import com.test.smartsearch.payload.searchmovies.SearchMovieResponse;
import com.test.smartsearch.repository.SearchKeywordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetSearchSuggestions {
    private final SearchKeywordRepository searchKeywordRepository;

    public SearchMovieResponse getSearchSuggestions(String query,  int pageNo, int pageSize, String sortBy, String sortDir) {
        log.info("Get search suggestions {}", query);
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable moviePages = PageRequest.of(pageNo, pageSize, sort);

        Page<Movie> movieSuggestions = getSuggestions(query, pageNo, pageSize, moviePages);

        List<SearchMovieInfo> movieInfos = MovieMapper.INSTANCE.moviesToSearchMovieInfos(movieSuggestions.getContent());

        SearchMovieResponse response = MovieMapper.INSTANCE.moviePagesToSearchMovieResponse(movieSuggestions, movieInfos);

        log.info("Get search suggestions {}", response);
        return response;
    }

    private Page<Movie> getSuggestions(String query, int pageNo, int pageSize, Pageable moviePages) {
        Optional<SearchKeyWord> keyword = searchKeyWord(query);

        if (keyword.isPresent()) {
            log.info("Finding Suggestions for: {}", keyword.get().getKeyword());

            List<Movie> moviesList = keyword.get().getMovies();

            int start = Math.min(pageNo * pageSize, moviesList.size());
            int end = Math.min((pageNo + 1) * pageSize, moviesList.size());
            List<Movie> moviesPageContent = moviesList.subList(start, end);

            return new PageImpl<>(moviesPageContent, moviePages, moviesList.size());
        } else {
            log.info("No keyword found for query: {}", query);
            return Page.empty();
        }
    }

    private Optional<SearchKeyWord> searchKeyWord(String query) {
        return searchKeywordRepository.findById(query);
    }
}

