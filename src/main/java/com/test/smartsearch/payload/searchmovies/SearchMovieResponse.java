package com.test.smartsearch.payload.searchmovies;

import lombok.Builder;

import java.util.List;

@Builder
public record SearchMovieResponse(
        List<SearchMovieInfo> movies,
        int pageNo,
        int pageSize,
        Long totalElements,
        int totalPages,
        boolean last
) {}
