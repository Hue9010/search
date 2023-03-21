package com.hue.search.repository;

import com.hue.search.model.SearchKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SearchKeywordRepository extends JpaRepository<SearchKeyword, Long> {
    List<SearchKeyword> findTop10ByOrderByCountDescUpdatedAtDesc();

    Optional<SearchKeyword> findByKeyword(String keyword);
}
