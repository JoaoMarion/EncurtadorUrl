package com.example.urlshortener.controller.response;

import lombok.Builder;

@Builder
public record UrlResponse(String shortUrl, String Url, Long remainingDays, int clicks) {
}
