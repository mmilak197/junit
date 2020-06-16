package com.devmmilak.junit.workshop;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StreamWorkshopTest {
    // mvn -Dtest=StreamWorkshopTest test

    // sut - System Unit Test
    StreamWorkshop sut = new StreamWorkshop();


    @Test
    public void shouldReturnDoubleAverage() {
        var numbers = Arrays.asList(2, 3, 4);
        var expected = 3.0;
        var actual = sut.average(numbers);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldReturnUpperCase() {
        var text = Arrays.asList("q", "w", "e", "r", "t", "y");
        var expected = Arrays.asList("Q", "W", "E", "R", "T", "Y");
        var actual = sut.upperCase(text);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldReturnUpperCaseSecond() {
        var text = Arrays.asList("q", "w", "e", "r", "t", "y");
        var expected = Arrays.asList("Q", "W", "E", "R", "T", "Y");
        var actual = sut.upperCaseSecond(text);

        assertThat(actual).isEqualTo(expected);
    }

}