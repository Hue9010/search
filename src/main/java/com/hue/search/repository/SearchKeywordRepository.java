package com.hue.search.repository;

import com.hue.search.model.SearchKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface SearchKeywordRepository extends JpaRepository<SearchKeyword, Long> {
    List<SearchKeyword> findTop10ByOrderByCountDescUpdatedAtDesc();

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    Optional<SearchKeyword> findByKeyword(String keyword);
}
