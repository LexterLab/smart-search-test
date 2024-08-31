package com.test.smartsearch.payload.searchmovies;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.UUID;

@Builder
public record SearchMovieInfo(
        UUID id,
        @Schema(example = "Harry Potter")
        String name,
        @Schema(example = "Adventure")
        String genre
) {}
