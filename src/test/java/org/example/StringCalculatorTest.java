package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StringCalculatorTest {

    StringCalculator stringCalculator = new StringCalculator();

    @Test
    void shouldReturnZeroForEmptyString() {
        String input = "";
        assertThat(stringCalculator.calculate(input)).isEqualTo(0);
    }

    @Test
    void shouldReturnPassedSingleNumber() {
        String input = "123";
        int expectedOutput = 123;
        assertThat(stringCalculator.calculate(input)).isEqualTo(expectedOutput);
    }

    @Test
    void shouldReturnSumOfValuesForCommaSeparatedNumbers() {
        String input = "12,13";
        int expectedOutput = 25;
        assertThat(stringCalculator.calculate(input)).isEqualTo(expectedOutput);
    }

    @Test
    void shouldReturnSumOfValuesForNewLineNumbers() {
        String input = "12\n13";
        int expectedOutput = 25;
        assertThat(stringCalculator.calculate(input)).isEqualTo(expectedOutput);
    }

    private static Stream<Arguments> sumOfNumbersTestCases() {
        return Stream.of(
                Arguments.of("1,2,3", 6),
                Arguments.of("1\n2\n3", 6)
        );
    }

    @ParameterizedTest
    @MethodSource("sumOfNumbersTestCases")
    void shouldReturnSumOfThreeNumbers(String input, int expectedOutput) {
        assertThat(stringCalculator.calculate(input)).isEqualTo(expectedOutput);
    }

    @ParameterizedTest
    @ValueSource(strings = {"-1", "1,-2"})
    void shouldThrowExceptionForNegativeNumbers(String input) {
        assertThatThrownBy(() -> stringCalculator.calculate(input)).isInstanceOf(Exception.class);
    }

    private static Stream<Arguments> ignoreNumbersGreaterThanThousandTestCases() {
        return Stream.of(
                Arguments.of("1000", 1000),
                Arguments.of("1001", 0),
                Arguments.of("1010,123", 123)
        );
    }

    @ParameterizedTest
    @MethodSource("ignoreNumbersGreaterThanThousandTestCases")
    void shouldIgnoreNumbersGreaterThanThousand(String input, int expectedOutput) {
        assertThat(stringCalculator.calculate(input)).isEqualTo(expectedOutput);
    }

    private static Stream<Arguments> customDelimiterTestCases() {
        return Stream.of(
                Arguments.of("//#1#2", 3),
                Arguments.of("//#1#2,3", 6),
                Arguments.of("//$1$2\n4", 7)
        );
    }

    @ParameterizedTest
    @MethodSource("customDelimiterTestCases")
    void shouldAcceptCustomCharDelimiter(String input, int expectedOutput) {
        assertThat(stringCalculator.calculate(input)).isEqualTo(expectedOutput);
    }
}