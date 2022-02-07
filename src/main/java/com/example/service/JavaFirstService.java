package com.example.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@Service
public class JavaFirstService implements JavaFirstInterface{
    static Scheduler scheduler = Schedulers.newBoundedElastic(5, 10, "MyThreadGroup");

    private static Mono<String> getWebClientWithScheduler(String path) {
        return WebClient.create("http://localhost:8080" + path).get()
                .retrieve()
                .bodyToMono(String.class)
                .publishOn(scheduler);
    }

    private static Mono<String> getWebClientWithSchedulerAndDelay(String path) {
        return WebClient.create("http://localhost:8080" + path).get()
                .retrieve()
                .bodyToMono(String.class)
                .publishOn(scheduler).delayElement(Duration.ofSeconds(5));
    }

    public JavaFirstService () {}

    @Override
    public String getSimple() {
        return "11111";
    }

    @Override
    public Mono<String> getReact() {
        return Mono.just("22222");
    }

    @Override
    public Mono<String> getDelay() {
        return Mono.just("33333").delayElement(Duration.ofSeconds(5));
    }

    @Override
    public Flux<String> getDoubleRequest() {
        return getWebClient("/simple").mergeWith(getWebClient("/react"));
    }

//сделал два варианта:
//  один с вызовом одного и того же метода reactDelay, в работе которого изначально предусмотрена задержка
//  второй с последовательным вызовом всех методов, но через метод с задержкой getWebClientWithSchedulerAndDelay
    @Override
    public Flux<String> getMultipleRequest() {
        /*return getWebClientWithScheduler("/delay")
                .mergeWith(getWebClientWithScheduler("/delay"))
                .mergeWith(getWebClientWithScheduler("/delay"))
                .mergeWith(getWebClientWithScheduler("/delay"))
                .mergeWith(getWebClientWithScheduler("/delay"));*/

        return getWebClientWithSchedulerAndDelay("/simple")
                .mergeWith(getWebClientWithSchedulerAndDelay("/react"))
                .mergeWith(getWebClientWithScheduler("/delay"))
                .mergeWith(getWebClientWithSchedulerAndDelay("/double"))
                .mergeWith(getWebClientWithSchedulerAndDelay("/webclient"));
    }

    @Override
    public Mono<String> getWebClient(String path) {
        return WebClient.create("http://localhost:8080" + path).get()
                .retrieve()
                .bodyToMono(String.class);
    }
}