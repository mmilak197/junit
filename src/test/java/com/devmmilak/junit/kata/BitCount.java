package com.devmmilak.junit.kata;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Write a function that takes an (unsigned) integer as input, and returns the number of bits that are equal to
 * one in the binary representation of that number.
 * Example: The binary representation of 1234 is 10011010010, so the function should return 5 in this case
 * <p>
 * Nie korzystamy z metody bitCount w klasie Ineteger.
 */
public class BitCount {
    private static int countBits(int n) {
        char one = '1';
        return (int) Integer
                .toBinaryString(n)
                .chars()
                .filter(item -> item == one)
                .count();
    }

    @Test
    public void testGame() {
        assertEquals(1, BitCount.countBits(4));
        assertEquals(5, BitCount.countBits(1234));
        assertEquals(3, BitCount.countBits(7));
        assertEquals(2, BitCount.countBits(9));
        assertEquals(2, BitCount.countBits(10));
    }
}
//https://www.codewars.com/kata/526571aae218b8ee490006f4/solutions/java