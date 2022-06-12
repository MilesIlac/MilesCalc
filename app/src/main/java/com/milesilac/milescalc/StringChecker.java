package com.milesilac.milescalc;


import com.milesilac.milescalc.common.CalculatorContract;


public class StringChecker implements CalculatorContract.StringChecker {

    private CalculatorContract.InputLogic inputLogic;
    private int OPCount = 0;
    private int CPCount = 0;
    private boolean hasOP = false;

    public StringChecker(CalculatorContract.InputLogic inputLogic) { this.inputLogic = inputLogic; }

    public StringChecker() { }


    @Override
    public boolean attemptToAddParentheses() {
        String[] ops = {"+","-","×","÷","%","("};
        boolean endsWithOp = false;
        for (String op : ops) {
            if (inputLogic.readCurrentString().endsWith(op)
                    || inputLogic.readCurrentString().isEmpty()) { endsWithOp = true; }
        }
        return endsWithOp;
    }

    @Override
    public void setOPCount(String count) {
        if (count.equals("plus")) OPCount++;
        else OPCount--;
        System.out.println("OPCount: " + OPCount);
    }

    @Override
    public void setCPCount(String count) {
        if (count.equals("plus")) CPCount++;
        else CPCount--;
        System.out.println("CPCount: " + CPCount);
    }

    @Override
    public boolean getOPStatus() { return hasOP; }

    @Override
    public void setOPStatus(boolean o) { this.hasOP = o; }

    @Override
    public boolean checkPCounts() { return OPCount == CPCount; }


}
