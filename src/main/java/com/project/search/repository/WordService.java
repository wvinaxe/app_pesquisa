package com.project.search.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import jakarta.transaction.Transactional;

import java.util.Map;
import java.util.HashMap;
import org.springframework.stereotype.Service;

import com.project.search.model.Word;


@Service
public class WordService {
    private final WordRepository wordRepository;

    public WordService(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }


    @Transactional
    public static Map<String, Integer> countWordOccurrences(String content, String normalizedPhrase) {
        String[] words = normalizedPhrase.split("\\s+");
        Map<String, Integer> wordOccurrences = new HashMap<>();

        for (String word : words) {
            int count = 0;
            int index = content.indexOf(word);

            while (index != -1) {
                if (Character.isLetter(content.charAt(index)) && word.length() == NormalizeInputService.normalizeInput(word).length()) {
                    count++;
                }
                index = content.indexOf(word, index + 1);
            }
            wordOccurrences.put(word, count);
        }

        return wordOccurrences;
     }

    public Optional<Word> findById(Long Id) {
        return this.wordRepository.findById(Id);
    }

    public List<Word> getAll() {
        return new ArrayList<>(wordRepository.findAll());
    }

}