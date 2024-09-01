package com.test.smartsearch.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;
import java.util.List;

@RedisHash("keywords")
@Builder
@Getter
@Setter
public class SearchKeyWord {
    @Id
    private String keyword;
    private List<Movie> movies;
    private LocalDateTime lastUpdates;
}


