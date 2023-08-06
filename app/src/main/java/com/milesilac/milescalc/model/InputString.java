package com.milesilac.milescalc.model;

import com.milesilac.milescalc.common.CalculatorContract;

public class InputString implements CalculatorContract.InputString {

    private String inputString = "";

    @Override
    public String getString() {
        return inputString;
    }

    @Override
    public void setString(String inputString) {
        this.inputString = inputString;
    }
}
