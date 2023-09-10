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

import com.project.search.exception.InvalidUrlException;
import com.project.search.exception.ResourceNotFoundException;
import com.project.search.model.Url;
import com.project.search.repository.UrlService;


    @RestController
    @RequestMapping("/url")

    public class UrlController {
        private final UrlService urlService;

        public UrlController(UrlService urlService) {
            this.urlService = urlService;
        }

        @GetMapping
        public List<Url> getAll() {
            return urlService.getAll();
        }

        @PostMapping
        @Transactional
        public ResponseEntity<?> create(@RequestBody @Validated Url newUrl, UriComponentsBuilder uriBuilder) {
            try {
                Url url = urlService.create(newUrl);
                URI uri = uriBuilder.path("/url/{id}").buildAndExpand(newUrl.getId()).toUri();
                return ResponseEntity.created(uri).body(url);
            } catch (InvalidUrlException exception) {
                return ResponseEntity.badRequest().body("URL inválida");
            }
        }

        @GetMapping("/{id}")
        public ResponseEntity<Url> read(@PathVariable Long id) {
            Optional<Url> url = urlService.findById(id);
            return url.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }

        @PutMapping("/{id}")
        @Transactional
        public ResponseEntity<Url> update(@PathVariable Long id, @RequestBody @Validated Url newUrl) throws ResourceNotFoundException {
            Url url = urlService.update(newUrl, id);
            return ResponseEntity.ok(url);
        }

        @DeleteMapping("/{id}")
        @Transactional
        public ResponseEntity<?> delete(@PathVariable Long id) throws ResourceNotFoundException {
            urlService.delete(id);
            return ResponseEntity.noContent().build();
        }
    }
