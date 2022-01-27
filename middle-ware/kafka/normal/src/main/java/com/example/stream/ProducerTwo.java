package com.example.stream;

import org.flywaydb.core.Flyway;

/**
 * @author simple
 */
public class ProducerTwo {
    public static void main(String[] args) {
        Flyway flyway = Flyway.configure().dataSource(url, user, password).load();
        flyway.migrate();
    }
}
