package com.test.smartsearch.payload.updatemovie;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

@Builder
public record UpdateMovieRequest(
        @JsonIgnore
        String movieId,
        @Schema(example = "Harry Potter")
        @NotBlank(message = "Field name cannot be blank")
        @Length(max = 100, message = "Field name must be max 100 chars")
        String name,
        @Schema(example = "Romance")
        @NotBlank(message = "Field genre cannot be null")
        @Length(min = 3, max = 100, message = "Field genre must be 3-100 chars")
        String genre
) {}
