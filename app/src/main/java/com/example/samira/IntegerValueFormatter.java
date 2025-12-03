package com.example.samira;

import com.github.mikephil.charting.formatter.ValueFormatter;

public class IntegerValueFormatter extends ValueFormatter {
    @Override
    public String getFormattedValue(float value) {
        return String.valueOf((int) value); // Retourne la valeur entière sans décimales
    }
}
