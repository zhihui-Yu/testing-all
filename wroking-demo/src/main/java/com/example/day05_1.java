package com.example;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class day05_1 {
    public static void main(String[] args) throws Exception {
        String path = day05_1.class.getClassLoader().getResource("day05_1.txt").getPath();
        try (var reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {
            String line;
            while ((line = reader.readLine()) != null) {

            }
        }
    }
}
