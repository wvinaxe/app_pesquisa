package com.project.search.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.project.search.model.Search;


public interface SearchRepository extends JpaRepository<Search, Long> {
    Optional<Search> findBySearchId(Long searchId);
}
