package com.example;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class day08_2 {
    public static void main(String[] args) throws IOException {
        /**
         *  size = 2 -> 1
         *  size = 3 -> 7
         *  size = 4 -> 4
         *  size = 5 -> 2,3,5
         *  size = 6 -> 0,6,9
         *  size = 7 -> 8
         *
         *  size = 5 ->
         *      1. 只有 3 才包含 数字1的字母
         *      2. 5 的字母都存在6中
         *
         *  size = 6 ->
         *      1. 6 的字母没有包含 1
         *      2. 0,9 的字母包含 1
         *      3. 9 的字母包含4
         *      4. 0 字母没有包含4
         */
        Map<List<String>, List<String>> map = new HashMap<>(200);
        try (var reader = FileReader.reader("day08_2.txt")) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] splitLine = line.split("\s\\|\s");
                List<String> list1 = new LinkedList<>(Arrays.asList(splitLine[0].split("\s+")));
                List<String> list2 = new LinkedList<>(Arrays.asList(splitLine[1].split("\s+")));
                map.put(list1, list2);
            }
        }

        List<Integer> res = new LinkedList<>();
        AtomicInteger count = new AtomicInteger();

        map.forEach((k, v) -> {
            Map<Integer, String> numMap = new HashMap<>();
            k.sort(Comparator.comparingInt(String::length));
            numMap.put(1, k.get(0));
            numMap.put(7, k.get(1));
            numMap.put(4, k.get(2));
            numMap.put(8, k.get(9));
            for (int i = 6; i < 9; i++) {
                String num = k.get(i);
                if (!in(num, numMap.get(1))) {
                    numMap.put(6, num);
                } else if (in(num, numMap.get(4))) {
                    numMap.put(9, num);
                } else {
                    numMap.put(0, num);
                }
            }

            for (int i = 3; i < 6; i++) {
                String num = k.get(i);
                if (in(num, numMap.get(1))) {
                    numMap.put(3, num);
                } else if (in(numMap.get(6), num)) {
                    numMap.put(5, num);
                } else numMap.put(2, num);
            }

//            Map<Integer, Integer> tmpMap = new HashMap<>();
//            numMap.forEach((x, y) -> tmpMap.put(y.chars().reduce(Integer::sum).getAsInt(), x));

            List<Integer> numResList = v.stream().map(num -> {
                for (var entry : numMap.entrySet()) {
                    if (eq(numMap.get(entry.getKey()), num)) return entry.getKey();
                }
                return null;
            }).collect(Collectors.toList());
            System.out.println(numResList);

            res.add(calc(numResList));

            count.addAndGet((int) numResList.stream().filter(n -> n == 1 || n == 4 || n == 7 || n == 8).count());
        });
        System.out.println(res);
        System.out.println(res.stream().reduce(Integer::sum).get());
        System.out.println(count);
    }

    /**
     * target: abc, obj: ab
     *
     * @return obj in target
     */
    private static boolean in(String target, String obj) {
        if (obj.length() == 1) return target.contains(obj);
        for (int i = 0; i < obj.length(); i++) {
            if (!in(target, String.valueOf(obj.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    private static boolean eq(String target, String obj) {
        if (target.length() != obj.length()) return false;
        return in(target, obj);
    }

    private static Integer calc(List<Integer> numList) {
        int result = 0;
        boolean firstZero;
        for (int i = 0; i < numList.size(); i++) {
            if (numList.get(i) == 0 && result == 0) {
                firstZero = true;
            } else firstZero = false;
            if (!firstZero)
                result = result * 10 + numList.get(i);
        }
        return result;
    }
}
