package com.test.smartsearch.payload.searchmovies;

import com.test.smartsearch.model.Movie;

import java.util.List;

public record SearchMovieResponse(
        List<Movie> movies,
        int pageNo,
        int pageSize,
        Long totalElements,
        int totalPages,
        boolean last
) {}
