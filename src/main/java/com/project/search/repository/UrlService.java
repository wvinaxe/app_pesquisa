package com.project.search.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.springframework.stereotype.Service;

import com.project.search.exception.InvalidUrlException;
import com.project.search.exception.ResourceNotFoundException;
import com.project.search.model.Url;

@Service
public class UrlService {
    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public Url create(Url url) throws InvalidUrlException {
        if (!isValidUrl(url.getUrl())) {
            throw new InvalidUrlException("A URL é inválida");
        }
        return this.urlRepository.save(url);
    }

    public Optional<Url> findById(Long id) {
        return this.urlRepository.findById(id);
    }

    public List<Url> getAll() {
        return new ArrayList<>(urlRepository.findAll());

    }

    public Url update(Url newUrl, Long id) throws ResourceNotFoundException {
        Optional<Url> optionalCustomer = this.findById(id);
        Url url = optionalCustomer.orElseThrow(() -> new ResourceNotFoundException(""));
        url.setUrl(newUrl.getUrl());
        url.setContent(newUrl.getContent());
        return url;
    }

    public void delete(Long id) throws ResourceNotFoundException {
        Optional<Url> optionalUrl = this.findById(id);
        Url url = optionalUrl.orElseThrow(() -> new ResourceNotFoundException(""));
        this.urlRepository.delete(url);
    }

    public Optional<Url> findByUrl(String url) {
        return this.urlRepository.findByUrl(url);
    }

    private boolean isValidUrl(String url) {
        if (url == null || url.isEmpty()) {
            return false;
        }

        String regex = "^(http://|https://)?[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}(/\\S*)?$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);

        return matcher.matches();
    }

    public static String getContentFromURL(String url) throws IOException {
        URL urlObj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        StringBuilder content = new StringBuilder();

        try (Scanner scanner = new Scanner(connection.getInputStream())) {
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine());
            }
        }
        
        String normalizedContent = NormalizeInputService.normalizeInput(content.toString());
        return normalizedContent;
     }
}