package com.example.javafirst.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import static c.e.javafirst.service.JavaFirstService.*;

@RestController
public class JavaFirstController {


    @GetMapping("/simple")
    public String simple() {
        return "11111";
    }

    @GetMapping("/react")
    public Mono<String> react() {
        return Mono.just("22222");
    }

    @GetMapping("/reactDelay")
    public Mono<String> reactDelay() {
        return getDelay();
    }

    @GetMapping("/double")
    public Flux<String> doubleRequest() {
        return getDoubleRequest();
    }

    @GetMapping("/multiple")
    public Flux<String> multipleRequest() {
        return getMultipleRequest();
    }

    @GetMapping("/webclient")
    public Mono<String> webClient() {
        return getWebClient("/react");
    }
}
