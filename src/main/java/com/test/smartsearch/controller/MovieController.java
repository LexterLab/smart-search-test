package com.test.smartsearch.controller;

import com.test.smartsearch.payload.searchmovies.SearchMovieResponse;
import com.test.smartsearch.payload.updatemovie.UpdateMovieRequest;
import com.test.smartsearch.payload.updatemovie.UpdateMovieResponse;
import com.test.smartsearch.service.GetSearchSuggestions;
import com.test.smartsearch.service.SearchMovies;
import com.test.smartsearch.service.UpdateMovie;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@Tag(name = "Movie REST APIs")
@RequiredArgsConstructor
public class MovieController {
    private final SearchMovies searchMovies;
    private final GetSearchSuggestions getSearchSuggestions;
    private final UpdateMovie updateMovie;

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

    @PatchMapping("movies/{movieId}")
    public ResponseEntity<UpdateMovieResponse> updateMovie(@PathVariable String movieId,
                                                           @RequestBody UpdateMovieRequest request) {
        UpdateMovieRequest updateMovieRequest = UpdateMovieRequest
                .builder()
                .genre(request.genre())
                .movieId(movieId)
                .name(request.name())
                .build();

        return new ResponseEntity<>(updateMovie.updateMovie(updateMovieRequest), HttpStatus.OK);
    }

}
