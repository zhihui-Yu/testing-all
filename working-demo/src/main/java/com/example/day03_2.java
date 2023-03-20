package com.example;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class day03_2 {
    private static final String PATH = "C:\\Users\\Administrator\\IdeaProjects\\testing-all\\wroking-demo\\src\\main\\resources\\day03_2.txt";

    public static void main(String[] args) throws Exception {
        Map<Integer, List<String>> map = new HashMap<>();
        try (var reader = new BufferedReader(new InputStreamReader(new FileInputStream(PATH)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                putLineInMap(map, new StringBuilder(line).toString(), 0);
            }
        }
        int index = 0;
        String one = calcOne(map, index + 1);
        String two = calcTwo(map, index + 1);
        System.out.println(one + " --- " + two);
        System.out.println(Integer.valueOf(one, 2) * Integer.valueOf(two, 2));
    }

    private static String calcTwo(Map<Integer, List<String>> map, int index) {
        int zeroSize = map.getOrDefault(0, List.of()).size();
        int oneSize = map.getOrDefault(1, List.of()).size();
        if (zeroSize == 0) return map.get(1).get(0);
        if (oneSize == 0) return map.get(0).get(0);
// 101010000100
        Map<Integer, List<String>> res = new HashMap<>();
        if (zeroSize <= oneSize) {
            map.get(0).forEach(line -> putLineInMap(res, line, index));
        } else {
            map.get(1).forEach(line -> putLineInMap(res, line, index));
        }

        return calcTwo(res, index + 1);
    }

    private static String calcOne(Map<Integer, List<String>> map, int index) {
        int zeroSize = map.getOrDefault(0, List.of()).size();
        int oneSize = map.getOrDefault(1, List.of()).size();
        if (zeroSize == 0) return map.get(1).get(0);
        if (oneSize == 0) return map.get(0).get(0);
        if (oneSize == 1 && zeroSize == 1) return map.get(1).get(0);
// 011001100111
        Map<Integer, List<String>> res = new HashMap<>();
        if (oneSize >= zeroSize) {
            map.get(1).forEach(line -> putLineInMap(res, line, index));
        } else {
            map.get(0).forEach(line -> putLineInMap(res, line, index));
        }
        return calcOne(res, index + 1);
    }

    private static void putLineInMap(Map<Integer, List<String>> res, String line, int index) {
        res.compute(line.charAt(index) - '0', (key, oldValue) -> {
            if (oldValue != null) {
                oldValue.add(line);
                return oldValue;
            } else {
                var values = new LinkedList<String>();
                values.add(line);
                return values;
            }
        });
    }
}