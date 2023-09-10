package com.project.search.repository;


import org.springframework.stereotype.Service;

@Service
public class NormalizeInputService {

    public static String normalizeInput(String input) {
        input = input.replaceAll("<.*?>", ""); // Remover tags HTML
        input = input.replaceAll("\\p{Punct}", " "); // Substituir pontua??es por espa?os
        input = input.toLowerCase(); // Converter para min?sculas
        input = input.replaceAll("[^a-z0-9\\s]", ""); // Remover caracteres especiais, mantendo letras e n?meros
        input = input.replaceAll("\\s+", " "); // Remover espa?os em branco extras

        return input;
     }
}
