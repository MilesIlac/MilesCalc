package com.milesilac.milescalc;

import com.milesilac.milescalc.common.CalculatorContract;

public class CalculationLogic implements CalculatorContract.CalculationLogic {

    CalculatorContract.View view;

    public CalculationLogic(CalculatorContract.View view) {
        this.view = view;
    }


    @Override
    public String solveString(String currentString) {
        while (currentString.contains("(") && currentString.contains(")")) {
            int innerOPIndex = currentString.lastIndexOf("(");
            int innerCPIndex = currentString.indexOf(")");
            String currentExpressionToSolve = currentString.substring(innerOPIndex,innerCPIndex+1);
            System.out.println("current to solve: " + currentExpressionToSolve);

        }
        return currentString;
    }
}
