package com.milesilac.milescalc;


import android.util.Log;

import com.milesilac.milescalc.common.CalculatorContract;


public class CalculationLogic implements CalculatorContract.CalculationLogic {

    private final CalculatorContract.StringChecker stringChecker = new StringChecker();

    public CalculationLogic() {
    }


    @Override
    public String solveString(String currentString) {
        String currentSolved = currentString;
        if (stringChecker.checkPCounts()) {
            if (currentSolved.contains("(")) {
                while (currentSolved.contains("(")) {
                    int innerOPIndex = currentSolved.lastIndexOf("(");
                    int innerCPIndex = currentSolved.indexOf(")");
                    String currentExpToSolve = currentSolved.substring(innerOPIndex+1,innerCPIndex);
                    currentSolved = prepareToSolve(currentSolved,currentExpToSolve, innerOPIndex, innerCPIndex, true);
                    System.out.println("Current solved 1: " + currentSolved);
                }
                while (currentSolved.contains("+") || currentSolved.contains("×")
                        || currentSolved.contains("÷") || currentSolved.contains("%")) {
                    String currentExpToSolve = currentSolved;
                    currentSolved = prepareToSolve(currentSolved,currentExpToSolve, -1, -1, false);
                    System.out.println("Current solved 2: " + currentSolved);
                }
            }
            else {
                while (currentSolved.contains("+") || currentSolved.contains("×")
                        || currentSolved.contains("÷") || currentSolved.contains("%")) {
                    String currentExpToSolve = currentSolved;
                    currentSolved = prepareToSolve(currentSolved,currentExpToSolve, -1, -1, false);
                    System.out.println("Current solved 3: " + currentSolved);
                }
            }
        }
        else {
            Log.i("ERROR?","Grouping Error; please check amount of parentheses");
        }

        if (currentSolved.endsWith(".0")) {
            return currentSolved.replace(".0","").trim();
        }
        else { return currentSolved; }
    }


    private String prepareToSolve(String fullString, String s, int innerOPIndex, int innerCPIndex, boolean hasP) {
        //--
        String currentSolved = s;
        int determineOps = 0;
        if (s.contains("×") || s.contains("÷")
                || s.contains("%")) {
            if (s.contains("×")) { determineOps += 2; }
            if (s.contains("÷")) { determineOps += 3; }
            if (s.contains("%")) { determineOps += 4; }
            switch (determineOps) {
                case 2: currentSolved = toSolve(fullString, s,"×", innerOPIndex, innerCPIndex, hasP); break;
                case 3: currentSolved = toSolve(fullString, s,"÷", innerOPIndex, innerCPIndex, hasP); break;
                case 4: currentSolved = toSolve(fullString, s,"%", innerOPIndex, innerCPIndex, hasP); break;
                case 5:
                    if (s.indexOf("×") < s.indexOf("÷")) {
                        currentSolved = toSolve(fullString, s,"×", innerOPIndex, innerCPIndex, hasP); }
                    if (s.indexOf("÷") < s.indexOf("×")) {
                        currentSolved = toSolve(fullString, s,"÷", innerOPIndex, innerCPIndex, hasP); }
                    break;
                case 6:
                    if (s.indexOf("×") < s.indexOf("%")) {
                        currentSolved = toSolve(fullString, s,"×", innerOPIndex, innerCPIndex, hasP); }
                    if (s.indexOf("%") < s.indexOf("×")) {
                        currentSolved = toSolve(fullString, s,"%", innerOPIndex, innerCPIndex, hasP); }
                    break;
                case 7:
                    if (s.indexOf("÷") < s.indexOf("%")) {
                        currentSolved = toSolve(fullString, s,"÷", innerOPIndex, innerCPIndex, hasP); }
                    if (s.indexOf("%") < s.indexOf("÷")) {
                        currentSolved = toSolve(fullString, s,"%", innerOPIndex, innerCPIndex, hasP); }
                    break;
                case 9:
                    if (s.indexOf("×") < s.indexOf("%")
                            && s.indexOf("×") < s.indexOf("÷")) {
                        currentSolved = toSolve(fullString, s,"×", innerOPIndex, innerCPIndex, hasP); }
                    if (s.indexOf("%") < s.indexOf("×")
                            && s.indexOf("%") < s.indexOf("÷")) {
                        currentSolved = toSolve(fullString, s,"%", innerOPIndex, innerCPIndex, hasP); }
                    if (s.indexOf("÷") < s.indexOf("×")
                            && s.indexOf("÷") < s.indexOf("%")) {
                        currentSolved = toSolve(fullString, s,"÷", innerOPIndex, innerCPIndex, hasP); }
                    break;
            }
        }
        else if (s.contains("+")) { currentSolved = toSolve(fullString, s,"+", innerOPIndex, innerCPIndex, hasP); }
        else {
                if (hasP) {
                    fullString = fullString.replace(fullString.substring(innerOPIndex,innerCPIndex+1),s);
                    currentSolved = fullString;
                }
            }

        //--
        return currentSolved;
    }



    private String toSolve(String fullString, String currentExpressionToSolve, String op, int innerOPIndex, int innerCPIndex, boolean hasP) {
        int currentOpIndex = currentExpressionToSolve.indexOf(op);
        int leftOpIndex = -1;
        int rightOpIndex = -1;

        //check operation/s at left and right of current operation
        for (int i=0;i<currentOpIndex;i++) {
            if (currentExpressionToSolve.charAt(i) == '+'
                    || currentExpressionToSolve.charAt(i) == '×'
                    || currentExpressionToSolve.charAt(i) == '÷'
                    || currentExpressionToSolve.charAt(i) == '%') {
                leftOpIndex = i;
            }
        }
        for (int i=currentExpressionToSolve.length()-1;i>currentOpIndex;i--) {
            if (currentExpressionToSolve.charAt(i) == '+'
                    || currentExpressionToSolve.charAt(i) == '×'
                    || currentExpressionToSolve.charAt(i) == '÷'
                    || currentExpressionToSolve.charAt(i) == '%') {
                rightOpIndex = i;
            }
        }

        float a;
        float b;
        int determineCurrentSolved = 0;
        String newEquation = null;
        String newEquation2 = null;
        String solvedExpression = null;
        if (leftOpIndex != -1) { //if left operand exists
            a = Float.parseFloat(currentExpressionToSolve.substring(leftOpIndex+1,currentOpIndex));
            newEquation = currentExpressionToSolve.substring(0,leftOpIndex+1).trim();
            determineCurrentSolved += 1;
        }
        else {
            a = Float.parseFloat(currentExpressionToSolve.substring(0,currentOpIndex));
        }
        if (rightOpIndex != -1) { //if right operand exists
            b = Float.parseFloat(currentExpressionToSolve.substring(currentOpIndex+1,rightOpIndex));
            newEquation2 = currentExpressionToSolve.substring(rightOpIndex).trim();
            determineCurrentSolved += 2;
        }
        else {
            b = Float.parseFloat(currentExpressionToSolve.substring(currentOpIndex+1));
        }
        switch (op) {
            case "+": solvedExpression = String.valueOf(a+b); break;
            case "×": solvedExpression = String.valueOf(a*b); break;
            case "÷": solvedExpression = String.valueOf(a/b); break;
            case "%": solvedExpression = String.valueOf(a%b); break;
        }
        String currentSolved = null;
        switch (determineCurrentSolved) {
            case 0: currentSolved = solvedExpression; break;
            case 1: currentSolved = newEquation + solvedExpression; break;
            case 2: currentSolved = solvedExpression + newEquation2; break;
            case 3: currentSolved = newEquation + solvedExpression + newEquation2; break;
        }
        if (hasP) {
            assert currentSolved != null;
            if (currentSolved.contains("+") || currentSolved.contains("×") || currentSolved.contains("÷") || currentSolved.contains("%")) {
                fullString = fullString.replace(fullString.substring(innerOPIndex+1,innerCPIndex),currentSolved);
            }
            else {
                fullString = fullString.replace(fullString.substring(innerOPIndex,innerCPIndex+1),currentSolved);
            }
        }
        else {
            fullString = currentSolved;
        }

        return fullString;
    }


}
