package com.example;

import java.io.IOException;

public class day08_1 {
    public static void main(String[] args) throws IOException {
        // 算出模式
        // 计算 | 后的数字表示
        // 符合条件的数字有多少·
        int count = 0;
        try (var reader = FileReader.reader("day08_1.txt")) {
            String line;
            while ((line = reader.readLine()) != null) {
                for (var str : line.split("\s+")) {
                    if (str.length() == 2 || str.length() == 3 || str.length() == 4 || str.length() == 7) {
                        count++;
                    }
                }
            }
        }
        System.out.println(count);
    }
}
