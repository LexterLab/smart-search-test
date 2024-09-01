package com.test.smartsearch.mapper;

import com.test.smartsearch.model.Movie;
import com.test.smartsearch.payload.searchmovies.SearchMovieInfo;
import com.test.smartsearch.payload.searchmovies.SearchMovieResponse;
import com.test.smartsearch.payload.updatemovie.UpdateMovieRequest;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MovieMapper {
    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

    SearchMovieInfo movieToSearchMovieInfo(Movie movie);

    List<SearchMovieInfo> moviesToSearchMovieInfos(List<Movie> movies);

    @Mapping(target = "movies", source = "movieInfos")
    @Mapping(target = "pageNo", expression = "java(page.getNumber())")
    @Mapping(target = "pageSize", expression = "java(page.getSize())")
    @Mapping(target = "totalElements", expression = "java(page.getTotalElements())")
    @Mapping(target = "totalPages", expression = "java(page.getTotalPages())")
    @Mapping(target = "last", expression = "java(page.isLast())")
    SearchMovieResponse moviePagesToSearchMovieResponse(Page<Movie> page, List<SearchMovieInfo> movieInfos);

//    @Mapping(target = "name", expression = "java(request.name())")
//    @Mapping(target = "genre", expression = "java(request.genre())")
    void updateMovie(@MappingTarget Movie movie, UpdateMovieRequest request);

}
