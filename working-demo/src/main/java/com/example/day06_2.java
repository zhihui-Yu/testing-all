package com.example;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class day06_2 {
    public static void main(String[] args) throws Exception {
        List<Fish> fishList = new LinkedList<>();
        try (var reader = FileReader.reader("day06_2.txt")) {
            String line;
            while ((line = reader.readLine()) != null) {
                for (var num : line.split(",")) {
                    fishList.add(new Fish(Integer.parseInt(num)));
                }
            }
        }
        var all = new ArrayList<List<Fish>>();
        all.add(fishList);
        // 当 size = 220 && 堆内存设置10g，超出。
        for (int i = 0; i < 200; i++) {
            List<List<Fish>> overall = new LinkedList<>();
            for (var list : all) {
                List<Fish> over = new LinkedList<>();
                List<Fish> tmp = new LinkedList<>();
                for (var fish : list) {
                    fish.down();
                    if (fish.hasNewOne()) {
                        tmp.add(new Fish(8));
                        fish.reset();
                    }
                }
                if (!tmp.isEmpty()) list.addAll(tmp);
                if (list.size() > 100 * 1000 * 1000) {
                    for (int j = 0; j < list.size(); j++) {
                        over.add(list.get(i));
                        list.remove(i);
                    }
                    overall.add(over);
                }
            }
            all.addAll(overall);
        }
        count(all);
    }


    private static void count(List<List<Fish>> all) {
        long count = 0;
        for (var list : all) {
            count += list.size();
        }
        System.out.println(count);
    }

    private static void calc(List<List<Fish>> all, int size) {

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
