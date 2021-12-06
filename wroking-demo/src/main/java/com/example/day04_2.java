package com.example;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class day04_2 {
    private static final String PATH = "C:\\Users\\Administrator\\IdeaProjects\\testing-all\\wroking-demo\\src\\main\\resources\\day04_2.txt";

    public static void main(String[] args) throws Exception {
        List<Integer> nums = new ArrayList<>();
        List<Entry> entries = new ArrayList<>();
        try (var reader = new BufferedReader(new InputStreamReader(new FileInputStream(PATH)))) {
            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    for (var num : line.split(",")) {
                        nums.add(Integer.parseInt(num));
                    }
                    firstLine = false;
                }
                if (line.equals("")) {
                    Entry entry = new Entry();
                    for (int i = 0; i < 5; i++) {
                        entry.addNums(reader.readLine());
                    }
                    entries.add(entry);
                }
            }
        }

        List<Entry> finishedEntries = new ArrayList<>();
        for (var num : nums) {
            for (Entry entry : entries) {
                if (finishedEntries.contains(entry) && finishedEntries.size() < entries.size()) continue;
                entry.lightNum(num);
                if (entry.isComplete()) finishedEntries.add(entry);
                if (finishedEntries.size() == entries.size()) {
                    System.out.println(num);
                    System.out.println(entry.calc(num));
                }
            }
        }
    }

    public static class Entry {
        Num[][] nums = new Num[5][5];
        int row = 0, col;

        public void addNums(String line) {
            String[] strs = line.split("\s+");
            for (var s : strs) {
                if (s.equals("")) continue;
                nums[row][col] = new Num(Integer.parseInt(s));
                col++;
            }
            row++;
            col = 0;
        }

        public void lightNum(int num) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (nums[i][j].num == num) {
                        nums[i][j].light();
                    }
                }
            }
        }

        public boolean isComplete() {
            int i = 0, j = 0;
            while (nums[0][j++].light) {
                if (j == 5) return true;
            }
            j = 0;
            while (nums[1][j++].light) {
                if (j == 5) return true;
            }
            j = 0;
            while (nums[2][j++].light) {
                if (j == 5) return true;
            }
            j = 0;
            while (nums[3][j++].light) {
                if (j == 5) return true;
            }
            j = 0;
            while (nums[4][j++].light) {
                if (j == 5) return true;
            }
            j = 0;

            while (nums[i++][0].light) {
                if (i == 5) return true;
            }
            i = 0;
            while (nums[i++][1].light) {
                if (i == 5) return true;
            }
            i = 0;
            while (nums[i++][2].light) {
                if (i == 5) return true;
            }
            i = 0;
            while (nums[i++][3].light) {
                if (i == 5) return true;
            }
            i = 0;
            while (nums[i++][4].light) {
                if (i == 5) return true;
            }
            i = 0;
            return false;
        }

        public int calc(int lightNum) {
            int res = 0;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (!nums[i][j].light) res += nums[i][j].num;
                }
            }
            return res * lightNum;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    sb.append("[").append(nums[i][j].num).append(",").append(nums[i][j].light).append("]").append("\t");
                }
                sb.append("\n");
            }
            return sb.toString();
        }
    }

    public static class Num {
        int num;
        boolean light;

        public Num(int num) {
            this.num = num;
            this.light = false;
        }

        public void light() {
            this.light = true;
        }

        @Override
        public String toString() {
            return "Num{" +
                    "num=" + num +
                    ", light=" + light +
                    '}';
        }
    }
}
