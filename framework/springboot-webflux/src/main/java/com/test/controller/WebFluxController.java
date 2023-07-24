package com.test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

/**
 * @author simple
 */
@RestController
public class WebFluxController {

    @ResponseBody
    @RequestMapping(value = "/flux")
    public Flux<Integer> get() {
        System.out.println(Thread.currentThread().getName() + "-run /flux");
        Flux<Integer> res = Flux.fromStream(this::execute);
        System.out.println("out /flux");
        return res;
    }

    @RequestMapping(value = "/flux-delay")
    public Flux<Integer> get4Delay() {
        System.out.println(Thread.currentThread().getName() + "-run /flux-delay");
        Flux<Integer> res = Flux.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 0)
//            .map(x -> x / 0)
            .delayElements(Duration.ofSeconds(1)); // 一个一个元素返回: 滚动输出
        System.out.println("out /flux-delay");
        return res;
    }

    private Stream<Integer> execute() {
        try {
            System.out.println(Thread.currentThread().getName() + "-start business");
            Thread.sleep(5000);
            System.out.println("run business");
            return Stream.of(1, 2, 3, 4, 5);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Stream.of();
    }
}
