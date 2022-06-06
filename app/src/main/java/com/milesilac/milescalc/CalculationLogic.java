package com.milesilac.milescalc;

import com.milesilac.milescalc.common.CalculatorContract;

public class CalculationLogic implements CalculatorContract.CalculationLogic {

    CalculatorContract.View view;

    public CalculationLogic(CalculatorContract.View view) {
        this.view = view;
    }


}
