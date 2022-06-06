package com.milesilac.milescalc;


//checks
//if there is a parenthesis
//if string is empty

import com.milesilac.milescalc.common.CalculatorContract;

public class StringChecker implements CalculatorContract.StringChecker {

    CalculatorContract.InputLogic inputLogic;
    int OPCount = 0;
    int CPCount = 0;


    public StringChecker(CalculatorContract.InputLogic inputLogic) {
        this.inputLogic = inputLogic;
    }

    @Override
    public boolean attemptToAddParentheses() {
        String[] ops = {"+","-","×","÷","%","("};
        boolean endsWithOp = false;
        for (String op : ops) {
            if (inputLogic.readCurrentString().endsWith(op) || inputLogic.readCurrentString().isEmpty()) {
                endsWithOp = true;
            }
        }
        return endsWithOp;
    }

    @Override
    public int setOPCount(String count) {
        if (count.equals("plus")) {
            OPCount++;

        }
        else {
            OPCount--;
        }
        System.out.println("OPCount: " + OPCount);
        return OPCount;
    }

    @Override
    public int setCPCount(String count) {
        if (count.equals("plus")) {
            CPCount++;
        }
        else {
            CPCount--;
        }
        System.out.println("CPCount: " + CPCount);
        return CPCount;
    }

    @Override
    public boolean checkPCounts() {
        return OPCount == CPCount;
    }


}
