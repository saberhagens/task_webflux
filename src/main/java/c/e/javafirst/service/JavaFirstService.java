package c.e.javafirst.service;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

public class JavaFirstService {
    static Scheduler scheduler = Schedulers.newBoundedElastic(5, 10, "MyThreadGroup");

    public static Mono<String> getDelay() {
        return Mono.just("33333").delayElement(Duration.ofSeconds(5));
    }

    public static Mono<String> getWebClient(String path) {
        return WebClient.create("http://localhost:8080" + path).get()
                .retrieve()
                .bodyToMono(String.class);
    }

    public static Mono<String> getWebClientWithScheduler(String path) {
        return WebClient.create("http://localhost:8080" + path).get()
                .retrieve()
                .bodyToMono(String.class)
                .publishOn(scheduler);
    }

    public static Mono<String> getWebClientWithSchedulerAndDelay(String path) {
        return WebClient.create("http://localhost:8080" + path).get()
                .retrieve()
                .bodyToMono(String.class)
                .publishOn(scheduler).delayElement(Duration.ofSeconds(5));
    }

    public static Flux<String> getDoubleRequest() {
        return getWebClient("/simple").mergeWith(getWebClient("/react"));
    }

//сделал два варианта:
//  один с вызовом одного и того же метода reactDelay, в работе которого изначально предусмотрена задержка
//  второй с последовательным вызовом всех методов, но через метод с задержкой getWebClientWithSchedulerAndDelay

    public static Flux<String> getMultipleRequest() {
        /*return getWebClientWithScheduler("/reactDelay")
                .mergeWith(getWebClientWithScheduler("/reactDelay"))
                .mergeWith(getWebClientWithScheduler("/reactDelay"))
                .mergeWith(getWebClientWithScheduler("/reactDelay"))
                .mergeWith(getWebClientWithScheduler("/reactDelay"));*/

        return getWebClientWithSchedulerAndDelay("/simple")
                .mergeWith(getWebClientWithSchedulerAndDelay("/react"))
                .mergeWith(getWebClientWithScheduler("/reactDelay"))
                .mergeWith(getWebClientWithSchedulerAndDelay("/double"))
                .mergeWith(getWebClientWithSchedulerAndDelay("/webclient"));
    }
}
