package com.test.smartsearch.controller;

import com.test.smartsearch.payload.searchmovies.SearchMovieResponse;
import com.test.smartsearch.service.GetSearchSuggestions;
import com.test.smartsearch.service.SearchMovies;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
@Tag(name = "Movie REST APIs")
@RequiredArgsConstructor
public class MovieController {
    private final SearchMovies searchMovies;
    private final GetSearchSuggestions getSearchSuggestions;

    @GetMapping("movies")
    public ResponseEntity<SearchMovieResponse> searchMovies(
            @RequestParam String query,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        return new ResponseEntity<>(searchMovies.searchMovies(query, pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @GetMapping("suggestions")
    public ResponseEntity<SearchMovieResponse> getSuggestions(
            @RequestParam String query,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        return new ResponseEntity<>(getSearchSuggestions.getSearchSuggestions(query, pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }


}
