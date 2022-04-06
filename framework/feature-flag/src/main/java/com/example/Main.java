package com.example;

import io.rollout.client.ConfigurationFetchedHandler;
import io.rollout.client.FetcherResults;
import io.rollout.client.FetcherStatus;
import io.rollout.rox.server.Rox;
import io.rollout.rox.server.RoxOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.ZonedDateTime;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        SpringApplication.run(Main.class, args);
    }
}