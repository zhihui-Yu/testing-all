package com.example;

import java.util.*;

public class day07_1 {

    public static Map<Integer, Integer> initialMap(int max) {
        Map<Integer, Integer> map = new HashMap<>();
        int count = 0;
        for (int i = 0; i <= max; i++) {
            count += i;
            map.put(i, count);
        }
        return map;
    }

    public static void main(String[] args) throws Exception {
        List<Integer> nums = new LinkedList<>();
        int max = 0;
        try (var reader = FileReader.reader("day07_2.txt")) {
            String line;
            while ((line = reader.readLine()) != null) {
                for (var numStr : line.split(",")) {
                    int n = Integer.parseInt(numStr);
                    nums.add(n);
                    max = Math.max(max, n);
                }
            }
        }
        System.out.println("max: " + max);
        Map<Integer, Integer> map = initialMap(max);

        int size = nums.size();
        int[][] dp = new int[max][2];
        for (int i = 0; i < max; i++) {
            dp[i][0] = i;
            for (Integer num : nums) {
//                System.out.println(i + "," + num);
//                dp[i][1] += Math.abs(num - dp[i][0]); // part one
                dp[i][1] += map.get(Math.abs(num - dp[i][0]));
            }
        }
        int minIndex = 0;
        for (int i = 0; i < size; i++) {
            if (dp[i][1] < dp[minIndex][1]) minIndex = i;
        }
        System.out.println(dp[minIndex][1]);
    }
}
