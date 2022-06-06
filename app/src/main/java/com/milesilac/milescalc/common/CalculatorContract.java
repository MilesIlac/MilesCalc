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
        void deleteLastChar();
    }

    interface StringChecker {
        boolean attemptToAddParentheses();
        int setOPCount(String count);
        int setCPCount(String count);
        boolean checkPCounts();
    }


    interface CalculationLogic {

    }


}
