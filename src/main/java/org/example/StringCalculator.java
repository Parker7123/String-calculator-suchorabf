package org.example;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StringCalculator {
    public int calculate(String arg) {
        if (arg.isEmpty()) {
            return 0;
        }
        String pattern = "[,\n";
        if (arg.startsWith("//")) {
            pattern += arg.charAt(2);
            arg = arg.substring(3);
        }
        return splitAndSum(arg, pattern +"]");
    }

    private int splitAndSum(String arg, String pattern) {
        var partialStrings = arg.split(pattern);
        var numbers =  Arrays.stream(partialStrings).map(Integer::parseInt).toList();
        checkInvalidNumbers(numbers);
        return numbers.stream().filter(this::filterOutWrongNumber).mapToInt(value -> value).sum();
    }

    private void checkInvalidNumbers(List<Integer> numbers) {
        if (numbers.stream().anyMatch(number -> number < 0)) {
            throw new NumberFormatException("Passed negative number");
        }
    }

    private boolean filterOutWrongNumber(Integer i) {
        return  i <= 1000;
    }
}
