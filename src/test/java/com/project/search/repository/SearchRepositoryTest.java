package com.project.search.repository;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.search.model.Url;
import com.project.search.model.Phrase;
import com.project.search.model.Search;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

import java.util.HashSet;


@SpringBootTest
public class SearchRepositoryTest {

    private SearchRepository searchRepository;

    @Test
    public void testSaveSearch() {

        Url url = new Url("http://www.google.com.br", null);

        // Criando um conjunto vazio de frases
        HashSet<Phrase> phrases = new HashSet<>();

        // Criando uma instância de Search com Url e conjunto de frases vazio
        Search search = new Search(url, phrases);

        // Salvando a instância no repositório
        searchRepository.save(search);

        // Verificando se o ID foi gerado
        Long id = search.getId();
        assertThat(id, notNullValue());
    }
}

