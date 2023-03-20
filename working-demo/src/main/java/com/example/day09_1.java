package com.example;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class day09_1 {
    public static void main(String[] args) throws IOException {
        List<List<Integer>> nums = new LinkedList<>();
        try (var reader = FileReader.reader("day09_1.txt")) {
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

        List<Integer> res = new LinkedList<>();
        for (int i = 1; i < rows - 1; i++) {
            for (int j = 1; j < cols - 1; j++) {
                int num = rectangle[i][j];
                int up = rectangle[i - 1][j];
                int down = rectangle[i + 1][j];
                int left = rectangle[i][j - 1];
                int right = rectangle[i][j + 1];
                if (Math.min(up, Math.min(down, Math.min(left, right))) > num) {
                    res.add(num);
                }
            }
        }
        if (Math.min(rectangle[0][1], rectangle[1][0]) > rectangle[0][0]) {
            res.add(rectangle[0][0]);
        }
        if (Math.min(rectangle[0][cols - 2], rectangle[1][cols - 1]) > rectangle[0][cols - 1]) {
            res.add(rectangle[0][cols - 1]);
        }
        if (Math.min(rectangle[rows - 1][1], rectangle[rows - 2][0]) > rectangle[rows - 1][0]) {
            res.add(rectangle[rows - 1][0]);
        }
        if (Math.min(rectangle[rows - 1][cols - 2], rectangle[rows - 2][cols - 1]) > rectangle[rows - 1][cols - 1]) {
            res.add(rectangle[rows - 1][cols - 1]);
        }
        // first row
        for (int j = 1; j < cols - 1; j++) {
            int num = rectangle[0][j];
            int down = rectangle[1][j];
            int left = rectangle[0][j - 1];
            int right = rectangle[0][j + 1];
            if (Math.min(down, Math.min(left, right)) > num) {
                res.add(num);
            }
        }

        // last row
        for (int j = 1; j < cols - 1; j++) {
            int num = rectangle[rows - 1][j];
            int up = rectangle[rows - 2][j];
            int left = rectangle[rows - 1][j - 1];
            int right = rectangle[rows - 1][j + 1];
            if (Math.min(up, Math.min(left, right)) > num) {
                res.add(num);
            }
        }

        // first col
        for (int i = 1; i < rows - 1; i++) {
            int num = rectangle[i][0];
            int down = rectangle[i + 1][0];
            int up = rectangle[i - 1][0];
            int right = rectangle[i][1];
            if (Math.min(up, Math.min(down, right)) > num) {
                res.add(num);
            }
        }

        // last col
        for (int i = 1; i < rows - 1; i++) {
            int num = rectangle[i][cols - 1];
            int down = rectangle[i + 1][cols - 1];
            int up = rectangle[i - 1][cols - 1];
            int left = rectangle[i][cols - 2];
            if (Math.min(up, Math.min(down, left)) > num) {
                res.add(num);
            }
        }
        System.out.println(res);
        System.out.println(res.stream().reduce(Integer::sum).get() + res.size());
    }
}
