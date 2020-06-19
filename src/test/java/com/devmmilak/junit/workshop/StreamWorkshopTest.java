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

        assertThat(expected).isEqualTo(actual);
    }

    @Test
    public void shouldReturnUpperCase() {
        var text = Arrays.asList("q", "w", "e", "r", "t", "y");
        var expected = Arrays.asList("Q", "W", "E", "R", "T", "Y");
        var actual = sut.upperCase(text);

        assertThat(expected).isEqualTo(actual);
    }

    @Test
    public void shouldReturnUpperCaseSecond() {
        var text = Arrays.asList("q", "w", "e", "r", "t", "y");
        var expected = Arrays.asList("Q", "W", "E", "R", "T", "Y");
        var actual = sut.upperCaseSecond(text);

        assertThat(expected).isEqualTo(actual);
    }

    @Test
    public void shouldReturnListStringStartLetter_A() {
        var text = Arrays.asList("atropology", "box", "a", "attach", "alex", "aaa");
        var expected = Arrays.asList("atropology", "attach", "alex", "aaa");
        var actual = sut.search(text);

        assertThat(expected).isEqualTo(actual);
    }

    @Test
    public void shouldReturnCommaSeparetedString() {
        var numbers = Arrays.asList(1, 2, 3);
        var expected = new String("1,2,3");
        var actual = sut.getString(numbers);

        assertThat(expected).isEqualTo(actual);
    }

}