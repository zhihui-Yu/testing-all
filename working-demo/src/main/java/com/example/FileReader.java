package com.example;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

public class FileReader {
    public static BufferedReader reader(String fileName) throws FileNotFoundException {
        String path = day06_1.class.getClassLoader().getResource(fileName).getPath();
        return new BufferedReader(new InputStreamReader(new FileInputStream(path)));
    }
}
