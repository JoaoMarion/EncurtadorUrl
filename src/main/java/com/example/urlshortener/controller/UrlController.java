package com.example.urlshortener.controller;

import com.example.urlshortener.controller.request.UrlRequest;
import com.example.urlshortener.controller.response.UrlResponse;
import com.example.urlshortener.entity.Url;
import com.example.urlshortener.exceptions.UrlExpiredException;
import com.example.urlshortener.repository.UrlRepository;
import com.example.urlshortener.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @PostMapping("/shorten-url")
    public ResponseEntity<UrlResponse> shortenUrl(@RequestBody UrlRequest request){
        return ResponseEntity.ok().body(urlService.createShortUrl(request));
    }

    @GetMapping("/urls")
    public ResponseEntity<List<UrlResponse>> findAll(){
        return ResponseEntity.ok().body(urlService.allUrls());
    }

    @GetMapping("{id}")
    public ResponseEntity<Void> redirect(@PathVariable  String id){
        var url = urlService.findByShortUrl(id);

        if(url.getExpiresAt().isBefore(LocalDateTime.now())){
            throw new UrlExpiredException("A URL expirou");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation((URI.create(url.getFullUrl())));

        url.setClickCount(url.getClickCount()+1);
        urlService.save(url);

        return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
    }
}
