package com.milesilac.milescalc.common;

public interface CalculatorContract {

    interface InputString {
        String getInputString();
        void setInputString(String inputString);
    }

    interface View {

    }

    interface InputLogic {
        void inputNum(String num);
        void inputOp(String op);
        String readCurrentString();
        String readActualCurrentStringToSolve();
        void deleteLastChar();
    }

    interface StringChecker {
        boolean attemptToAddParentheses();
        int setOPCount(String count);
        int setCPCount(String count);
        boolean getOPStatus();
        void setOPStatus(boolean o);
        boolean checkPCounts();
    }


    interface CalculationLogic {
        String solveString(String currentString);
    }


}
