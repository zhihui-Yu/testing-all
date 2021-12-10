package com.example;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * @author simple
 */
public class day10_2 {
    public static void main(String[] args) throws Exception {
        List<Long> scores = new LinkedList<>();
        Map<String, Integer> scoreMap = Map.of(")", 1, "]", 2, "}", 3, ">", 4);

        try (var reader = FileReader.reader("day10_2.txt")) {
            String line;
            while ((line = reader.readLine()) != null) {
                Stack<Character> stack = new Stack<>();
                long lineScore = 0;
                boolean needComplete = false;
                stack.push(line.charAt(0));
                System.out.println(line);
                for (int i = 1; i < line.length(); i++) {
                    char c = line.charAt(i);
                    try {
                        Character latestOne = stack.peek();
                        if (c == '(' || c == '[' || c == '{' || c == '<') {
                            stack.add(c);
                        } else if (latestOne.equals('(') && c == ')') {
                            stack.pop();
                        } else if (latestOne.equals('[') && c == ']') {
                            stack.pop();
                        } else if (latestOne.equals('{') && c == '}') {
                            stack.pop();
                        } else if (latestOne.equals('<') && c == '>') {
                            stack.pop();
                        } else break;
                    } catch (Exception exception) {
                        System.out.println(i);
                        stack.add(c);
                    }

                    if (i == line.length() - 1 && !stack.isEmpty()) {
                        needComplete = true;
                        int size = stack.size();
                        for (int j = 0; j < size; j++) {
                            Character ch = stack.pop();
                            if (ch.equals('(')) lineScore = lineScore * 5 + scoreMap.get(")");
                            else if (ch.equals('[')) lineScore = lineScore * 5 + scoreMap.get("]");
                            else if (ch.equals('{')) lineScore = lineScore * 5 + scoreMap.get("}");
                            else if (ch.equals('<')) lineScore = lineScore * 5 + scoreMap.get(">");
                        }
//                        score += scoreMap.get(String.valueOf(c));
                    }
                }
                if (needComplete) scores.add(lineScore);
            }
        }
        scores.sort((Long::compareTo));
        System.out.println(scores);
        System.out.println(scores.get(scores.size() / 2));
    }
}
