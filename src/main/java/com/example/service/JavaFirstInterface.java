package com.example.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface JavaFirstInterface {
    String getSimple();

    Mono<String> getReact();

    Mono<String> getDelay();

    Flux<String> getDoubleRequest();

    Flux<String> getMultipleRequest();

    Mono<String> getWebClient(String path);
}
