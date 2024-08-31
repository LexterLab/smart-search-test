package com.test.smartsearch.repository;

import com.test.smartsearch.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface MovieRepository extends JpaRepository<Movie, UUID> {

    @Query("""
        SELECT m FROM Movie m
        WHERE m.name LIKE %?1%
        OR m.genre LIKE %?1%
        ORDER BY m.name ASC
    """)
    Page<Movie> search(String query, Pageable pageable);
}
