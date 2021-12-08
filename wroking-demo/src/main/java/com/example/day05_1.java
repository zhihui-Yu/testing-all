package com.example;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class day05_1 {
    public static void main(String[] args) throws Exception {
        List<Line> lines = new LinkedList<>();
        String path = day05_1.class.getClassLoader().getResource("day05_2.txt").getPath();
        try (var reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] s1 = line.split(" -> ");
                String[] split1 = s1[0].split(",");
                String[] split2 = s1[1].split(",");
                lines.add(new Line(
                    new Point(Integer.parseInt(split1[0]), Integer.parseInt(split1[1])),
                    new Point(Integer.parseInt(split2[0]), Integer.parseInt(split2[1]))
                ));
            }
        }
//        System.out.println(lines);
        int size = 1000;
        int[][] rectangle = new int[size][size];
        for (var line : lines) {
            System.out.println(line);
            putLineToRect(rectangle, line);
        }

        int count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (rectangle[i][j] >= 2) count++;
//                System.out.printf(rectangle[i][j] + ",");
            }
//            System.out.println();
        }
        System.out.println(count);


    }

    private static void putLineToRect(int[][] lines, Line line) {
        if (!line.isHorizontalLine() && !line.isVerticalLine() && !line.isDiagonal()) return;
        if (line.isDiagonal()) {
            boolean reversed = line.isReverseLine();
            int index = reversed ? line.xEnd : line.xStart;
            for (int i = line.yStart; i <= line.yEnd; i++) {
                lines[i][index] += 1;
                if (reversed) index -= 1;
                else index += 1;
            }
        } else if (line.isHorizontalLine()) {
            for (int i = line.xStart; i <= line.xEnd; i++) {
                lines[line.yStart][i] += 1;
            }
        } else {
            for (int i = line.yStart; i <= line.yEnd; i++) {
                lines[i][line.xStart] += 1;
            }
        }
    }

    public static class Line {
        public Point p1;
        public Point p2;
        public int xStart;
        public int xEnd;
        public int yStart;
        public int yEnd;

        public Line(Point p1, Point p2) {
            this.p1 = p1;
            this.p2 = p2;

            xStart = Math.min(p1.x, p2.x);
            xEnd = Math.max(p1.x, p2.x);
            yStart = Math.min(p1.y, p2.y);
            yEnd = Math.max(p1.y, p2.y);
        }

        public boolean isReverseLine() {
            return isDiagonal() && (p1.x - p2.x) / (p1.y - p2.y) == -1;
        }

        public boolean isDiagonal() {
            return p1.x - p2.x == p1.y - p2.y || p1.x - p2.x == -(p1.y - p2.y);
        }

        public boolean isHorizontalLine() {
            return Objects.equals(p1.y, p2.y);
        }

        public boolean isVerticalLine() {
            return Objects.equals(p1.x, p2.x);
        }

        @Override
        public String toString() {
            return "Line{" +
                "p1=" + p1 +
                ", p2=" + p2 +
                '}';
        }
    }

    public static class Point {
        public Integer x;
        public Integer y;

        public Point(Integer x, Integer y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
        }
    }
}
