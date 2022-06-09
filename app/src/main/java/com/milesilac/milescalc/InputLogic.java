package com.milesilac.milescalc;


import android.util.Log;

import com.milesilac.milescalc.common.CalculatorContract;
import com.milesilac.milescalc.models.InputString;

public class InputLogic implements CalculatorContract.InputLogic {

    CalculatorContract.View view;
    CalculatorContract.StringChecker stringChecker = new StringChecker(this);
    InputString inputString = new InputString();


    public InputLogic(CalculatorContract.View view) {
        this.view = view;
    }


    @Override
    public void inputNum(String number) {
        inputString.setInputString(inputString.getInputString() + number);
    }

    @Override
    public void inputOp(String op) {
        if (op.equals("parentheses")) {
            if (!stringChecker.getOPStatus()) {
                if (stringChecker.attemptToAddParentheses()) {
                    inputString.setInputString(inputString.getInputString() + "(");
                }
                else {
                    inputString.setInputString(inputString.getInputString() + "×(");
                }
                stringChecker.setOPCount("plus");
                stringChecker.setOPStatus(true);
            }
            else {
                if (stringChecker.attemptToAddParentheses()) {
                    inputString.setInputString(inputString.getInputString() + "(");
                    stringChecker.setOPCount("plus");
                    stringChecker.setOPStatus(true);
                }
                else {
                    inputString.setInputString(inputString.getInputString() + ")");
                    stringChecker.setCPCount("plus");
                    if (inputString.getInputString().endsWith(")")) {
                        stringChecker.setOPStatus(!stringChecker.checkPCounts());
                    }
                    else stringChecker.setOPStatus(false);
                }
            }
        }
        else if (op.equals("-")) {
            inputString.setInputString(inputString.getInputString() + "+-");
            Log.i("check","Actual string: " + inputString.getInputString());
        }
        else {
            inputString.setInputString(inputString.getInputString() + op);
        }

    }

    @Override
    public String readCurrentString() {
        if (inputString.getInputString().contains("+-")) {
            return inputString.getInputString().replace("+-","-");
        }
        if (inputString.getInputString().endsWith(".0")) {
            return inputString.getInputString().replace(".0","").trim();
        }
        else return inputString.getInputString();
    }

    @Override
    public String readActualCurrentStringToSolve() {
        return inputString.getInputString();
    }

    @Override
    public void deleteLastChar() {
        if (!inputString.getInputString().isEmpty()) {
            if (inputString.getInputString().endsWith("(")) {
                String newInputString = inputString.getInputString().substring(0,inputString.getInputString().length()-1).trim();
                stringChecker.setOPCount("minus");
                inputString.setInputString(newInputString);
            }
            else if (inputString.getInputString().endsWith(")")) {
                String newInputString = inputString.getInputString().substring(0,inputString.getInputString().length()-1).trim();
                stringChecker.setCPCount("minus");
                inputString.setInputString(newInputString);
            }
            else if (inputString.getInputString().endsWith("+-")) {
                String newInputString = inputString.getInputString().substring(0,inputString.getInputString().length()-2).trim();
                inputString.setInputString(newInputString);
            }
            else {
                String newInputString = inputString.getInputString().substring(0,inputString.getInputString().length()-1).trim();
                inputString.setInputString(newInputString);
            }

        }
    }
}
