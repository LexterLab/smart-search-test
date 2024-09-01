package com.test.smartsearch.payload.updatemovie;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record UpdateMovieRequest(
        @JsonIgnore
        String movieId,
        @Schema(example = "Harry Potter")
        String name,
        @Schema(example = "Romance")
        String genre
) {}
