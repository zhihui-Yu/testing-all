package com.example;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class day09_2 {
    public static void main(String[] args) throws IOException {
        List<List<Integer>> nums = new LinkedList<>();
        try (var reader = FileReader.reader("day09_2.txt")) {
            String line;
            while ((line = reader.readLine()) != null) {
                List<Integer> list = new LinkedList<>();
                line.chars().forEach(c -> list.add(c - '0'));
                nums.add(list);
            }
        }

        int rows = nums.size();
        int cols = nums.get(0).size();
        int[][] rectangle = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rectangle[i][j] = nums.get(i).get(j);
            }
        }

        int[][] dp = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                dp[i][j] = count(i, j, rectangle).size() + 1;
            }
        }
        List<Integer> res = new LinkedList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (dp[i][j] > 1) {
                    res.add(dp[i][j]);
                }
            }
        }
        res.sort(Integer::compareTo);
        System.out.println(res);
        System.out.println(res.get(res.size() - 1) * res.get(res.size() - 2) * res.get(res.size() - 3));
    }

    public static Set<String> count(int row, int col, int[][] rectangle) {
        Set<String> set = new TreeSet<>();
//        if (row < 0 || row >= rectangle.length || col < 0 || col >= rectangle[0].length) return set;
        int center = rectangle[row][col];
        // up
        if (row > 0 && rectangle[row - 1][col] != 9 && rectangle[row - 1][col] > center) {
            set.add((row - 1) + "-" + col);
            set.addAll(count(row - 1, col, rectangle));
        }
        // down
        if (row < rectangle.length - 1 && rectangle[row + 1][col] != 9 && rectangle[row + 1][col] > center) {
            set.add((row + 1) + "-" + col);
            set.addAll(count(row + 1, col, rectangle));
        }
        // left
        if (col > 0 && rectangle[row][col - 1] != 9 && rectangle[row][col - 1] > center) {
            set.add(row + "-" + (col - 1));
            set.addAll(count(row, col - 1, rectangle));
        }
        // right
        if (col < rectangle[0].length - 1 && rectangle[row][col + 1] != 9 && rectangle[row][col + 1] > center) {
            set.add(row + "-" + (col + 1));
            set.addAll(count(row, col + 1, rectangle));
        }
        return set;
    }
}
