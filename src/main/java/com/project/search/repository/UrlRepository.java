package com.project.search.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.search.model.Url;

public interface UrlRepository extends JpaRepository<Url, Long> {
    Optional<Url> findByUrl(String url);
}