package com.example;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author simple
 */
public class Day03 {
    public static void main(String[] args) throws IOException {
        Map<Integer, Integer> map = new HashMap<>();
        int count = 0;
        try (var reader = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\simple\\IdeaProjects\\testing-all\\wroking-demo\\src\\main\\resources\\day03.txt")))) {
            while (reader.readLine() != null) {
                count++;
                char[] chars = reader.readLine().toCharArray();
                if (map.isEmpty()) {
                    for (int i = 0; i < chars.length; i++) {
                        map.put(i, chars[i] - '0');
                    }
                } else {
                    for (int i = 0; i < chars.length; i++) {
                        map.put(i, map.get(i) + (chars[i] - '0'));
                    }
                }
            }
        }
        System.out.println(map);

        StringBuilder res = new StringBuilder("");
        StringBuilder nres = new StringBuilder("");
        int finalCount = count;
        map.forEach((i, s) -> {
            if (s > finalCount / 2) {
                res.append(1);
                nres.append(0);
            } else {
                nres.append(1);
                res.append(0);
            }
        });
        // 10111100100  1508
        // 01000011011  539
        System.out.println("res: " + res + ", -> " + Integer.valueOf(res.toString(), 2));
        System.out.println("nres: " + nres + ", -> " + Integer.valueOf(nres.toString(), 2));
        System.out.println(Integer.valueOf(res.toString(), 2) * Integer.valueOf(nres.toString(), 2));
    }
}
