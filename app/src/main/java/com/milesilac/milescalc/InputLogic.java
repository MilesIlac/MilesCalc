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
            if (stringChecker.attemptToAddParentheses()) {
                if (stringChecker.checkPCounts()) {
                    inputString.setInputString(inputString.getInputString() + "(");
                    stringChecker.setOPCount("plus");
                }
                else {
                    inputString.setInputString(inputString.getInputString() + ")");
                    stringChecker.setCPCount("plus");
                }
            }
            else {
                if (stringChecker.checkPCounts()) {
                    inputString.setInputString(inputString.getInputString() + "×(");
                    stringChecker.setOPCount("plus");
                }
                else {
                    inputString.setInputString(inputString.getInputString() + ")");
                    stringChecker.setCPCount("plus");
                }
            }
        }
        if (op.equals("-")) {
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
