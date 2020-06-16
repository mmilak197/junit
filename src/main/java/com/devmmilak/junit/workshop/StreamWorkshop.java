package com.devmmilak.junit.workshop;

import java.util.List;
import java.util.stream.Collectors;

public class StreamWorkshop {


    public Double average(List<Integer> list) {
        return list.stream()
                .mapToInt(it -> it)
                .average()
                .getAsDouble();
    }

    public List<String> upperCase(List<String> text) {
        return text.stream()
                .map(it -> it.toUpperCase())
                .collect(Collectors.toList());
    }

    public List<String> upperCaseSecond(List<String> text) {
        return text.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }
}
