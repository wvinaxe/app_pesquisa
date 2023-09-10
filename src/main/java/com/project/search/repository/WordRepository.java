package com.project.search.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.search.model.Word;

public interface WordRepository extends JpaRepository<Word, Long> {

    Optional<Word> findByWord(String word);
}