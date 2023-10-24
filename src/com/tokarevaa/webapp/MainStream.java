package com.tokarevaa.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MainStream {

    public static void main(String[] args) {
        System.out.println(minValue(new int[]{5, 1, 2, 3, 3, 2, 3}));
        System.out.println(oddOrEven(new ArrayList<>(Arrays.asList(1, 2, 3, 4, 7, 1))).toString());
    }

    private static int minValue(int[] values) {
        AtomicInteger minValue = new AtomicInteger();
        Arrays.stream(values).sorted().distinct().forEach(
                o -> minValue.accumulateAndGet(o, (u, v) -> u * 10 + v));
        return minValue.get();
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        int intSum = integers.stream().mapToInt(v -> v).sum() % 2;
        return integers.stream().mapToInt(v -> v).filter(i -> i % 2 != intSum).boxed().collect(Collectors.toList());
    }
}