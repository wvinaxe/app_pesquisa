package com.project.search.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.project.search.exception.ResourceNotFoundException;
import com.project.search.model.Phrase;


@Service
public class PhraseService {
    private final PhraseRepository phraseRepository;

    public PhraseService(PhraseRepository phraseRepository) {
        this.phraseRepository = phraseRepository;
    }

    public Phrase create(Phrase phrase) {
        return this.phraseRepository.save(phrase);
    }

    public Optional<Phrase> findById(Long id) {
        return this.phraseRepository.findById(id);
    }

    public List<Phrase> getAll() {
        return new ArrayList<>(phraseRepository.findAll());

    }

    public Phrase update(Phrase newPhrase, Long id) throws ResourceNotFoundException {
        Optional<Phrase> optionalPhrase = this.findById(id);
        Phrase phrase = optionalPhrase.orElseThrow(() -> new ResourceNotFoundException(""));

        phrase.setPhrase(newPhrase.getPhrase());

        return phrase;
    }

    @Transactional
    public void delete(Long id) throws ResourceNotFoundException {
        Optional<Phrase> optionalPhrase = this.findById(id);
        Phrase phrase = optionalPhrase.orElseThrow(() -> new ResourceNotFoundException(""));
        this.phraseRepository.delete(phrase);
    }

    public static int countPhraseOccurrences(String content, String phrase) {
        int count = 0;
        int index = content.indexOf(phrase);

        while (index != -1) {
            count++;
            index = content.indexOf(phrase, index + 1);
        }

        return count;
     }

}