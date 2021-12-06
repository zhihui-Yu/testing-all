package com.example;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class day06_1 {
    public static void main(String[] args) throws Exception {
        List<Fish> fishList = new LinkedList<>();
        try (var reader = FileReader.reader("day06_1.txt")) {
            String line;
            while ((line = reader.readLine()) != null) {
                for (var num : line.split(",")) {
                    fishList.add(new Fish(Integer.parseInt(num)));
                }
            }
        }

        for (int i = 0; i < 80; i++) {
            List<Fish> tmp = new LinkedList<>();
            for (var fish : fishList) {
                fish.down();
                if (fish.hasNewOne()) {
                    tmp.add(new Fish(8));
                    fish.reset();
                }
            }
            fishList.addAll(tmp);
        }
        System.out.println(fishList.size());
    }

    public static class Fish {
        int timer;

        public Fish(int timer) {
            this.timer = timer;
        }

        public boolean hasNewOne() {
            return timer < 0;
        }

        public void reset() {
            this.timer = 6;
        }

        public void down() {
            this.timer--;
        }
    }
}
