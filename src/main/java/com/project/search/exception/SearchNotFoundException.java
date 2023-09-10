package com.project.search.exception;

public class SearchNotFoundException extends RuntimeException {

    public SearchNotFoundException(Long id) {
        super("Pesquisa não encontrada com o ID: " + id);
    }
}

