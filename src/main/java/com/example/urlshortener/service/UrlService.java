package com.example.urlshortener.service;

import com.example.urlshortener.controller.request.UrlRequest;
import com.example.urlshortener.controller.response.UrlResponse;
import com.example.urlshortener.entity.Url;
import com.example.urlshortener.mapper.UrlMapper;
import com.example.urlshortener.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.print.attribute.standard.PresentationDirection;
import java.net.URI;
import java.security.SecureRandom;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UrlService {
    private final UrlRepository urlRepository;



    public static String generateShortUrl() {
        byte[] randomBytes = new byte[6];
        SecureRandom random = new SecureRandom();
        random.nextBytes(randomBytes);
        String base64String = Base64.getUrlEncoder().encodeToString(randomBytes);
        return base64String.substring(0, 6);
    }




    public UrlResponse createShortUrl(UrlRequest request) {
        String shortUrl;
        do {
            shortUrl = generateShortUrl();
        } while (urlRepository.findByShortUrl(shortUrl).isPresent());

       Url url = UrlMapper.toUrl(request); url.setShortUrl(shortUrl);

       urlRepository.save(url);

       return UrlMapper.toResponse(url);

    }

    public List<UrlResponse> allUrls(){
        return urlRepository.findAll().stream()
                .map(UrlMapper::toResponse)
                .toList();
    }

   public Url findByShortUrl(String shortUrl){
        return urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new NoSuchElementException("Não encontrado"));
    }

    public void save(Url url){
        urlRepository.save(url);
    }


}
