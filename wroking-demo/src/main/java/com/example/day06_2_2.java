package com.example;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author simple
 */
public class day06_2_2 {
    public static final Map<String, Long> map = new HashMap<>();

    public static void main(String[] args) throws IOException {
        Map<Integer, Integer> fishMap = new HashMap<>();
        try (var reader = FileReader.reader("day06_2.txt")) {
            String line;
            while ((line = reader.readLine()) != null) {
                for (var num : line.split(",")) {
                    fishMap.compute(Integer.parseInt(num), (key, oldValue) -> {
                        if (oldValue == null) return 1;
                        return oldValue + 1;
                    });
                }
            }
        }
        // 80 -> 362666
        long res = 0;
        for (var entry : fishMap.entrySet()) {
            long count = count(257, entry.getKey());
            res += count * entry.getValue();
        }
        System.out.println(res);

//        System.out.println(count(257, 1));
    }

    public static long count(int day, int initialTimer) {
        if (map.get(day + "-" + initialTimer) != null) return map.get(day + "-" + initialTimer);
//        if (initialTimer + 2 > day) return 1;
        Set<Integer> babyDays = calc(day, initialTimer);
        long count = 1;
        for (var d : babyDays) {
            count += count(day - d + 1, 8);
        }
        System.out.println("day: " + day + ", count: " + count);
        map.put(day + "-" + initialTimer, count);
        return count;
    }

    private static Set<Integer> calc(int day, int initialTimer) {
        if (initialTimer + 2 > day) return Set.of();
        Set<Integer> list = new TreeSet<>();
        list.add(initialTimer + 2);
        for (int i = initialTimer + 2; i <= day; i = i + 7) {
            list.add(i);
        }
        return list;
    }
}
