package com.project.search.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@EntityScan
public class Phrase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String phrase;

    // Getters e setters

    @OneToOne
    private Search search;

    @OneToMany(mappedBy = "phrase", cascade = CascadeType.ALL)
    private List<Word> words = new ArrayList<>();

    // Método para adicionar uma palavra à frase
    public void addWord(Word word) {
        word.setPhrase(this);
        words.add(word);
    }

}
