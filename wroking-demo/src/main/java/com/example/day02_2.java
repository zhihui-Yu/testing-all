package com.example.jwt.utils;

import java.io.*;

public class day02_2 {
    private static final String PATH = "C:\\Users\\Administrator\\IdeaProjects\\testing-all\\wroking-demo\\src\\main\\resources\\day02_2.txt";

    public static void main(String[] args) throws FileNotFoundException {
        int left = 0;
        int down = 0;
        int depth = 0;
        try (var reader = new BufferedReader(new InputStreamReader(new FileInputStream(PATH)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] str = line.split(" ");
                String word = str[0];
                int num = Integer.parseInt(str[1]);
                if (word.equals("forward")) {
                    left += num;
                    depth += down * num;
                } else if (word.equals("down")) {
                    down += num;
                } else if (word.equals("up")) {
                    down -= num;
                }
            }
            System.out.println("left -> " + left);
            System.out.println("depth -> " + depth);
            System.out.println("left * depth => " + left * depth);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
