package com.test.smartsearch.repository;

import com.test.smartsearch.model.SearchKeyWord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchKeywordRepository extends CrudRepository<SearchKeyWord, String> {
}
