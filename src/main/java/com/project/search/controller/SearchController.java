package com.project.search.controller;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.project.search.exception.ResourceNotFoundException;
import com.project.search.model.Phrase;
import com.project.search.model.Search;
import com.project.search.repository.SearchService;


    @RestController
    @RequestMapping("/search")
    public class SearchController {
        private final SearchService searchService;

        public SearchController(SearchService searchService) {
            this.searchService = searchService;
        }

        @GetMapping("/index.html")
        public ModelAndView showForm() {
            return new ModelAndView("index");
        }
        @GetMapping("/resultado")
        public ModelAndView showResultado() {
            return new ModelAndView("resultado");
        }

        @PostMapping("/processar")
        public String processForm(@RequestParam("url") String url,
                                @RequestParam("phrase") String phrase,
                                Model model) throws IOException {
            String result = searchService.processText(url, phrase);

            model.addAttribute("result", result); // Adicionaso o resultado como atributo ao modelo

            return "redirect:/resultado"; // Nome da página HTML (resultado.html)
        }

        @PostMapping
        @Transactional
        public ResponseEntity<Search> create(@RequestBody @Validated Search newSearch, UriComponentsBuilder uriBuilder) {
            Search search = searchService.create(newSearch);
            URI uri = uriBuilder.path("/search/{id}").buildAndExpand(newSearch.getId()).toUri();
            return ResponseEntity.created(uri).body(search);
        }

        @GetMapping("/{urlId}")
        public ResponseEntity<Search> read(@PathVariable Long searchId) {
            Optional<Search> search = searchService.findBySearchId(searchId);
            return search.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }

        @PutMapping("/{id}")
        @Transactional
        public ResponseEntity<Search> addPhrase(@PathVariable Long id, @RequestBody @Validated Set<Phrase> newPhrase) throws ResourceNotFoundException {
            Search search = searchService.addPhrase(newPhrase, id);
            return ResponseEntity.ok(search);
        }

        @DeleteMapping("/{id}")
        @Transactional
        public ResponseEntity<?> delete(@PathVariable Long id){
            searchService.delete(id);
            return ResponseEntity.noContent().build();
        }

    }