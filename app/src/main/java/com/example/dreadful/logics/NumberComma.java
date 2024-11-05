package com.example.dreadful.logics;

public class NumberComma {
    public NumberComma() {

    }

    public String convertComma(int number) {
        String numberString = Integer.toString(number);
        StringBuilder result = new StringBuilder();

        int lap = 1;

        for (int i = numberString.length() - 1; i >= 0; i--) {
            result.append(numberString.charAt(i));
            if (lap == 3 && i > 0) { // Modified condition: i > 0
                result.append(",");
                lap = 1;
            } else {
                lap++;
            }
        }

        return result.reverse().toString();
    }
}
