package com.test.smartsearch.payload.updatemovie;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UpdateMovieResponse(
        UUID movieId
) {
}
