package com.example;

import java.util.Map;
import java.util.Stack;

/**
 * @author simple
 */
public class day10_1 {
    public static void main(String[] args) throws Exception {
        int score = 0;
        Map<String, Integer> scoreMap = Map.of(")", 3, "]", 57, "}", 1197, ">", 25137);

        try (var reader = FileReader.reader("day10_1.txt")) {
            Stack<Character> stack = new Stack<>();
            String line;
            while ((line = reader.readLine()) != null) {
                stack.push(line.charAt(0));
                for (int i = 1; i < line.length(); i++) {
                    Character latestOne = stack.peek();
                    char c = line.charAt(i);
                    if (c == '(' || c == '[' || c == '{' || c == '<') stack.add(c);
                    else if (latestOne.equals('(') && c == ')') {
                        stack.pop();
                    } else if (latestOne.equals('[') && c == ']') {
                        stack.pop();
                    } else if (latestOne.equals('{') && c == '}') {
                        stack.pop();
                    } else if (latestOne.equals('<') && c == '>') {
                        stack.pop();
                    } else {
                        score += scoreMap.get(String.valueOf(c));
                        break;
                    }
                }
            }
        }
        System.out.println(score);
    }
}
