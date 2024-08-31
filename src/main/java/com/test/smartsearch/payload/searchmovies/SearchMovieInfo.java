package com.test.smartsearch.payload.searchmovies;

import lombok.Builder;

import java.util.UUID;

@Builder
public record SearchMovieInfo(
        UUID id,
        String name,
        String genre
) {}
