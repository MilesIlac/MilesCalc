package com.milesilac.milescalc.common;


public interface CalculatorContract {

    interface InputString {
        String getString();
        void setString(String inputString);
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
        void setOPCount(String count);
        void setCPCount(String count);
        boolean getOPStatus();
        void setOPStatus(boolean o);
        boolean checkPCounts();
    }

    interface CalculationLogic {
        String solveString(String currentString);
    }

    interface ThreadManager {
        void execute(String s, ResultsGetter resultsGetter);
    }

    interface ResultsGetter {
        void results(String s);
    }

}
