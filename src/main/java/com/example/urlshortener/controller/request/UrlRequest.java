package com.example.urlshortener.controller.request;

import lombok.Builder;

@Builder
public record UrlRequest(String url, Long days) {
}
