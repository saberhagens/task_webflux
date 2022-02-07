package com.example.controller;

import com.example.service.JavaFirstInterface;
import com.example.service.JavaFirstService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class JavaFirstController {

    @Autowired
    JavaFirstInterface javaFirst =  new JavaFirstService();

    @GetMapping("/simple")
    public String simple() {
        return javaFirst.getSimple();
    }

    @GetMapping("/react")
    public Mono<String> react() {
        return javaFirst.getReact();
    }

    @GetMapping("/delay")
    public Mono<String> delay() {
        return javaFirst.getDelay();
    }

    @GetMapping("/double")
    public Flux<String> doubleRequest() {
        return javaFirst.getDoubleRequest();
    }

    @GetMapping("/multiple")
    public Flux<String> multipleRequest() {
        return javaFirst.getMultipleRequest();
    }

    @GetMapping("/webclient")
    public Mono<String> webClient() {
        return javaFirst.getWebClient("/react");
    }
}
