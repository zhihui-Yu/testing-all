package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author simple
 */
@RestController
public class KafkaController {
    @Autowired
    KafkaTemplate<String, String> template;

    @GetMapping(path = "/send/{msg}")
    public String sendMsg(@PathVariable String msg) {
        template.send("topic1", msg);
        return "ok";
    }
}
