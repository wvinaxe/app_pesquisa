package com.project.search.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.search.model.Phrase;

public interface PhraseRepository extends JpaRepository<Phrase, Long> {
}