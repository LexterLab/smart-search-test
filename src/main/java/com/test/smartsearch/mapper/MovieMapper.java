package com.test.smartsearch.mapper;

import com.test.smartsearch.model.Movie;
import com.test.smartsearch.payload.searchmovies.SearchMovieInfo;
import com.test.smartsearch.payload.searchmovies.SearchMovieResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface MovieMapper {
    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

    SearchMovieInfo movieToSearchMovieInfo(Movie movie);

    List<SearchMovieInfo> moviesToSearchMovieInfos(List<Movie> movies);

    SearchMovieResponse moviePagesToSearchMovieResponse(Page<Movie> page);
}
