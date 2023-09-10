package com.project.search.repository;

import java.util.Optional;
import java.util.Set;
import java.io.IOException;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.project.search.exception.ResourceNotFoundException;
import com.project.search.model.Phrase;
import com.project.search.model.Search;


@Service

public class SearchService {

    private final SearchRepository searchRepository;


    public SearchService(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    public Search create(Search search) {
        return this.searchRepository.save(search);
    }

    public Optional<Search> findBySearchId(Long searchId) {
        return this.searchRepository.findBySearchId(searchId);
    }

    public Search addPhrase(Set<Phrase> newPhrase, Long id) throws ResourceNotFoundException {
        Search search = this.searchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(""));
        search.getPhrase().addAll(newPhrase);
        return this.searchRepository.save(search);
    }

    public void delete(Long id) {
        Optional<Search> optionalSearch = this.searchRepository.findById(id);
        optionalSearch.ifPresent(this.searchRepository::delete);
    }

    public String processText(String url, String phrase) throws IOException {
        //chamando os m�todos para fazer an�lise do texto
        String content = UrlService.getContentFromURL(url);
        int phraseOccurrences = PhraseService.countPhraseOccurrences(content, phrase);
        Map<String, Integer> wordOccurrences = WordService.countWordOccurrences(content, phrase);

        //  criando uma representa��o de string dos resultados
        String result = "A frase '" + phrase + "' aparece " + phraseOccurrences + " vezes.\n";
        for (Map.Entry<String, Integer> entry : wordOccurrences.entrySet()) {
            result += "A palavra '" + entry.getKey() + "' aparece " + entry.getValue() + " vezes.\n";
        }

        // retornar essa representa��o de string como resultado
        return result;
    }

     public static void printWordOccurrences(Map<String, Integer> wordOccurrences) {
        for (Map.Entry<String, Integer> entry : wordOccurrences.entrySet()) {
            System.out.println("'" + entry.getKey() + "' foi repetida " + entry.getValue() + " vezes");
        }
     }


}

