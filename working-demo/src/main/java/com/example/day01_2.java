package com.example.jwt.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class day01_2 {
    private static final String PATH = "C:\\Users\\Administrator\\IdeaProjects\\testing-all\\wroking-demo\\src\\main\\resources\\day01_2.txt";

    public static void main(String[] args) throws IOException {
        List<Integer> nums = new LinkedList<>();
        try (var reader = new BufferedReader(new InputStreamReader(new FileInputStream(PATH)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                nums.add(Integer.parseInt(line));
            }
        }
        List<Integer> handledNums = new LinkedList<>();
        for (int i = 2; i < nums.size(); i++) {
            handledNums.add(nums.get(i) + nums.get(i - 1) + nums.get(i - 2));
        }
        int count = 0;
        for (int i = 1; i < handledNums.size(); i++) {
            if (handledNums.get(i) > handledNums.get(i - 1)) count++;
        }
        System.out.println(count);
    }
}
