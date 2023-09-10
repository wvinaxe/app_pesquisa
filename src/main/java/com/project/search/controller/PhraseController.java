package com.project.search.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.project.search.exception.ResourceNotFoundException;
import com.project.search.model.Phrase;
import com.project.search.repository.PhraseService;


@RestController
@RequestMapping("/phrase")

public class PhraseController {
    private final PhraseService phraseService;
    public PhraseController(PhraseService phraseService) {
        this.phraseService = phraseService;
    }

    @GetMapping
    public List<Phrase> getAll() {
        return phraseService.getAll();
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Phrase> create(@RequestBody @Validated Phrase newPhrase, UriComponentsBuilder uriBuilder) {
        Phrase phrase = phraseService.create(newPhrase);
        URI uri = uriBuilder.path("/phrase/{id}").buildAndExpand(newPhrase.getId()).toUri();
        return ResponseEntity.created(uri).body(phrase);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Phrase> read(@PathVariable Long id) {
        Optional<Phrase> phrase = phraseService.findById(id);
        return phrase.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Phrase> update(@PathVariable Long id, @RequestBody @Validated Phrase newPhrase) throws ResourceNotFoundException {
        Phrase phrase = phraseService.update(newPhrase, id);
        return ResponseEntity.ok(phrase);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id) throws ResourceNotFoundException {
        phraseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
