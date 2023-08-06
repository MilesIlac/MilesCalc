package com.milesilac.milescalc;


import android.util.Log;

import com.milesilac.milescalc.common.CalculatorContract;
import com.milesilac.milescalc.model.InputString;


public class InputLogic implements CalculatorContract.InputLogic {

    private final CalculatorContract.StringChecker stringChecker = new StringChecker(this);
    private final InputString inputString = new InputString();

    public InputLogic() {
    }


    @Override
    public void inputNum(String number) {
        inputString.setString(inputString.getString() + number);
    }

    @Override
    public void inputOp(String op) {
        if (op.equals("parentheses")) {
            if (!stringChecker.getOPStatus()) {
                if (stringChecker.attemptToAddParentheses()) {
                    inputString.setString(inputString.getString() + "(");
                } else {
                    inputString.setString(inputString.getString() + "×(");
                }
                stringChecker.setOPCount("plus");
                stringChecker.setOPStatus(true);
            } else {
                if (stringChecker.attemptToAddParentheses()) {
                    inputString.setString(inputString.getString() + "(");
                    stringChecker.setOPCount("plus");
                    stringChecker.setOPStatus(true);
                } else {
                    inputString.setString(inputString.getString() + ")");
                    stringChecker.setCPCount("plus");
                    if (inputString.getString().endsWith(")")) {
                        stringChecker.setOPStatus(!stringChecker.checkPCounts());
                    } else stringChecker.setOPStatus(false);
                }
            }
        } else if (op.equals("-")) {
            inputString.setString(inputString.getString() + "+-");
            Log.i("check", "Actual string: " + inputString.getString());
        } else {
            inputString.setString(inputString.getString() + op);
        }

    }

    @Override
    public String readCurrentString() {
        if (inputString.getString().contains("+-")) {
            return inputString.getString().replace("+-", "-");
        }
        if (inputString.getString().endsWith(".0")) {
            return inputString.getString().replace(".0", "").trim();
        } else return inputString.getString();
    }

    @Override
    public String readActualCurrentStringToSolve() {
        return inputString.getString();
    }

    @Override
    public void deleteLastChar() {
        if (!inputString.getString().isEmpty()) {
            if (inputString.getString().endsWith("(")) {
                String newString = inputString.getString().substring(0, inputString.getString().length() - 1).trim();
                stringChecker.setOPCount("minus");
                inputString.setString(newString);
            } else if (inputString.getString().endsWith(")")) {
                String newString = inputString.getString().substring(0, inputString.getString().length() - 1).trim();
                stringChecker.setCPCount("minus");
                inputString.setString(newString);
            } else if (inputString.getString().endsWith("+-")) {
                String newString = inputString.getString().substring(0, inputString.getString().length() - 2).trim();
                inputString.setString(newString);
            } else {
                String newString = inputString.getString().substring(0, inputString.getString().length() - 1).trim();
                inputString.setString(newString);
            }
        }
    }
}
