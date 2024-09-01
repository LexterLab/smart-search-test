package com.test.smartsearch.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.smartsearch.model.Movie;
import com.test.smartsearch.model.SearchKeyWord;
import com.test.smartsearch.payload.updatemovie.UpdateMovieRequest;
import com.test.smartsearch.repository.MovieRepository;
import com.test.smartsearch.repository.SearchKeywordRepository;
import com.test.smartsearch.route.RestAPIRoutes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class MovieControllerTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private SearchKeywordRepository searchKeywordRepository;

    @Autowired
    private ObjectMapper mapper;

    @AfterEach
    void tearDown() {
        searchKeywordRepository.deleteAll();
    }

    @Test
    void shouldRespondWithOKAndFoundMoviesWithoutCache() throws Exception {
        String query = "Mystery";

        long numberOfCachesBefore = searchKeywordRepository.count();

        mockMvc.perform(get(RestAPIRoutes.SEARCH_MOVIES)
                .param("query", query))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.movies[0].genre").value(query))
                .andExpect(jsonPath("$.movies[1].genre").value(query));

        long numberOfCachesAfter = searchKeywordRepository.count();

        assertTrue(numberOfCachesAfter > numberOfCachesBefore);
        assertEquals(1, numberOfCachesAfter);
    }

    @Test
    void shouldRespondWithOKAndFoundMoviesWithCache() throws Exception {
        String query = "Mystery";

        Movie whispersMovie = movieRepository.findById(UUID.fromString("473593c7-a07a-4dfa-b711-7e2c0ddea764"))
                .orElseThrow(() -> new AssertionError("Movie not found"));

        Movie hogwartsMovie = movieRepository.findById(UUID.fromString("dca392f0-b89a-490e-ba57-d2dfc0cd0900"))
                .orElseThrow(() -> new AssertionError("Movie not found"));

        SearchKeyWord keyWord = SearchKeyWord
                .builder()
                .movies(List.of(whispersMovie, hogwartsMovie))
                .keyword(query)
                .build();

        searchKeywordRepository.save(keyWord);

        long numberOfCachesBefore = searchKeywordRepository.count();

        mockMvc.perform(get(RestAPIRoutes.SEARCH_MOVIES)
                        .param("query", query))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.movies[0].genre").value(query))
                .andExpect(jsonPath("$.movies[1].genre").value(query));

        long numberOfCachesAfter = searchKeywordRepository.count();

        assertEquals(numberOfCachesAfter, numberOfCachesBefore);
        assertEquals(1, numberOfCachesAfter);
    }

    @Test
    void shouldRespondWithOKAndMovieSuggestionsWhenCacheFound() throws Exception {
        String query = "Mystery";

        Movie whispersMovie = movieRepository.findById(UUID.fromString("473593c7-a07a-4dfa-b711-7e2c0ddea764"))
                .orElseThrow(() -> new AssertionError("Movie not found"));

        Movie hogwartsMovie = movieRepository.findById(UUID.fromString("dca392f0-b89a-490e-ba57-d2dfc0cd0900"))
                .orElseThrow(() -> new AssertionError("Movie not found"));

        SearchKeyWord keyWord = SearchKeyWord
                .builder()
                .movies(List.of(whispersMovie, hogwartsMovie))
                .keyword(query)
                .build();

        searchKeywordRepository.save(keyWord);

        long numberOfCaches = searchKeywordRepository.count();

        mockMvc.perform(get(RestAPIRoutes.GET_SUGGESTIONS)
                        .param("query", query))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.movies[0].genre").value(query))
                .andExpect(jsonPath("$.movies[1].genre").value(query));

        assertEquals(1, numberOfCaches);
    }

    @Test
    void shouldRespondWithOKAndNoMovieSuggestionsWhenCacheNotFound() throws Exception {
        String query = "Mystery";

        long numberOfCaches = searchKeywordRepository.count();

        mockMvc.perform(get(RestAPIRoutes.GET_SUGGESTIONS)
                        .param("query", query))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movies").isEmpty())
                .andExpect(jsonPath("$.totalElements").value(0));

        assertEquals(0, numberOfCaches);
    }

    @Test
    void shouldRespondWithOKAndMovieIdAndUpdateCacheWhenUpdatingMovie() throws Exception {
        String id = "dca392f0-b89a-490e-ba57-d2dfc0cd0900";

        Movie hogwartsMovie = movieRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new AssertionError("Movie not found"));

        SearchKeyWord keyWord = SearchKeyWord
                .builder()
                .movies(List.of(hogwartsMovie))
                .keyword("Mystery")
                .build();

        searchKeywordRepository.save(keyWord);

        UpdateMovieRequest request = UpdateMovieRequest
                .builder()
                .genre("Mystery")
                .name("Hogwarts")
                .build();

        mockMvc.perform(patch(RestAPIRoutes.UPDATE_MOVIE, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movieId").value(id));

        Movie updatedMovie = movieRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new AssertionError("Movie not found"));

        assertEquals(request.genre(), updatedMovie.getGenre());
        assertEquals(request.name(), updatedMovie.getName());
    }

}