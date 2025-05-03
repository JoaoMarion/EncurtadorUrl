package com.example.urlshortener.mapper;

import com.example.urlshortener.controller.request.UrlRequest;
import com.example.urlshortener.controller.response.UrlResponse;
import com.example.urlshortener.entity.Url;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@UtilityClass
public class UrlMapper {
    public static Url toUrl(UrlRequest request){
        LocalDateTime expiresAt = LocalDateTime.now().plusDays(request.days());
        return Url.builder()
                .fullUrl(request.url())
                .expiresAt(expiresAt)
                .build();
    }

    public static UrlResponse toResponse(Url url){
        LocalDateTime now = LocalDateTime.now();
        long remainingDays = Math.max(0, ChronoUnit.DAYS.between(now, url.getExpiresAt()));
        String urlApi = "http://localhost:8080/";

        return UrlResponse.builder()
                .shortUrl(urlApi + url.getShortUrl())
                .Url(url.getFullUrl())
                .remainingDays(remainingDays)
                .clicks(url.getClickCount())
                .build();
    }

}
