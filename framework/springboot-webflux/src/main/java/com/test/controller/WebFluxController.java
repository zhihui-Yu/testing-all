package com.test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.stream.Stream;

/**
 * @author simple
 */
@RestController
public class WebFluxController {

    @RequestMapping("/flux")
    public Flux<Integer> get() {
        System.out.println("run /flux");
        Flux<Integer> res = Flux.fromStream(this::execute);
        System.out.println("out /flux");
        return res;
    }

    private Stream<Integer> execute() {
        try {
            Thread.sleep(5000);
            return Stream.of(1, 2, 3, 4, 5);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Stream.of();
    }
}
