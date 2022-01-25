package com.milesilac.testcalc;


import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView txtViewSmall,txtViewBig;
    HorizontalScrollView scroll;
    boolean hasOpeningParenthesis = false;
    int OPCount = 0;
    int CPCount = 0;
    String backgroundSequence = " ";
    int endCharIndex;
    int lastIndex = -1;
    int nextIndex = -1;
    int indexInnermostOParenthesis;
    int indexInnermostCParenthesis;
    String isSolved;
    int determineOps = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtViewSmall = findViewById(R.id.txtViewSmall);
        txtViewBig = findViewById(R.id.txtViewBig);
        scroll = findViewById(R.id.scroll);



    }


    public void onClick (View v) {
        switch (v.getId()) {
            case R.id.btn0:
                toInputChar("0");
                break;
            case R.id.btn1:
                toInputChar("1");
                break;
            case R.id.btn2:
                toInputChar("2");
                break;
            case R.id.btn3:
                toInputChar("3");
                break;
            case R.id.btn4:
                toInputChar("4");
                break;
            case R.id.btn5:
                toInputChar("5");
                break;
            case R.id.btn6:
                toInputChar("6");
                break;
            case R.id.btn7:
                toInputChar("7");
                break;
            case R.id.btn8:
                toInputChar("8");
                break;
            case R.id.btn9:
                toInputChar("9");
                break;
            case R.id.btnAdd:
                txtViewSmall.setText(txtViewSmall.getText()+"+");
                backgroundSequence = backgroundSequence.trim() + "+";
                break;
            case R.id.btnSubt:
                txtViewSmall.setText(txtViewSmall.getText()+"-");
                backgroundSequence = backgroundSequence.trim() + "+-";
                break;
            case R.id.btnMult:
                txtViewSmall.setText(txtViewSmall.getText()+"x");
                backgroundSequence = backgroundSequence.trim() + "x";
                break;
            case R.id.btnDiv:
                txtViewSmall.setText(txtViewSmall.getText()+"/");
                backgroundSequence = backgroundSequence.trim() + "/";
                break;
            case R.id.btnPercent:
                txtViewSmall.setText(txtViewSmall.getText()+"%");
                backgroundSequence = backgroundSequence.trim() + "%";
                break;
            case R.id.btnDot:
                txtViewSmall.setText(txtViewSmall.getText()+".");
                backgroundSequence = backgroundSequence.trim() + ".";
                break;
            case R.id.btnParenthesis:
                toCheckParenthesis();
                break;
            case R.id.btnEquals:
                toCalc();
                break;
            case R.id.btnC:
                toClear();
                break;
            case R.id.btnQuit:
                finish();
                break;

        }

    }


    public void toCalc() {
        if (OPCount == CPCount) {
            if (backgroundSequence.contains("(")) {
                while (backgroundSequence.contains("(")) {
                    indexInnermostOParenthesis = backgroundSequence.lastIndexOf("(");
                    System.out.println("Index of innermost ( is " + indexInnermostOParenthesis);
                    indexInnermostCParenthesis = backgroundSequence.indexOf(")",backgroundSequence.lastIndexOf("("));
                    System.out.println("Index of innermost ) is " + indexInnermostCParenthesis);
                    String testToSolve = backgroundSequence.substring(indexInnermostOParenthesis+1,indexInnermostCParenthesis);
                    System.out.println("First priority to solve: " + testToSolve);


                    if (testToSolve.contains("x") || testToSolve.contains("/") || testToSolve.contains("%")) {
                        if (testToSolve.contains("x")) {
                            determineOps = determineOps + 2;
                        }

                        if (testToSolve.contains("/")) {
                            determineOps = determineOps + 3;
                        }

                        if (testToSolve.contains("%")) {
                            determineOps = determineOps + 4;
                        }
                        System.out.println("Current value of determine ops: " + determineOps);
                        switch (determineOps) {
                            case 2:
                                toSolve(testToSolve,"x",true);
                                break;
                            case 3:
                                toSolve(testToSolve,"/",true);
                                break;
                            case 4:
                                toSolve(testToSolve,"%",true);
                                break;
                            case 5:
                                if (testToSolve.indexOf("x") < testToSolve.indexOf("/")) {
                                    toSolve(testToSolve,"x",true);
                                }
                                if (testToSolve.indexOf("/") < testToSolve.indexOf("x")) {
                                    toSolve(testToSolve,"/",true);
                                }
                                break;
                            case 6:
                                if (testToSolve.indexOf("x") < testToSolve.indexOf("%")) {
                                    toSolve(testToSolve,"x",true);
                                }
                                if (testToSolve.indexOf("%") < testToSolve.indexOf("x")) {
                                    toSolve(testToSolve,"%",true);
                                }
                                break;
                            case 7:
                                if (testToSolve.indexOf("/") < testToSolve.indexOf("%")) {
                                    toSolve(testToSolve,"/",true);
                                }
                                if (testToSolve.indexOf("%") < testToSolve.indexOf("/")) {
                                    toSolve(testToSolve,"%",true);
                                }
                                break;
                            case 9:
                                if (testToSolve.indexOf("x") < testToSolve.indexOf("%") && testToSolve.indexOf("x") < testToSolve.indexOf("/")) {
                                    toSolve(testToSolve,"x",true);
                                }
                                if (testToSolve.indexOf("%") < testToSolve.indexOf("x") && testToSolve.indexOf("%") < testToSolve.indexOf("/")) {
                                    toSolve(testToSolve,"%",true);
                                }
                                if (testToSolve.indexOf("/") < testToSolve.indexOf("x") && testToSolve.indexOf("/") < testToSolve.indexOf("%")) {
                                    toSolve(testToSolve,"/",true);
                                }
                                break;
                        }
                    }

                    else if (testToSolve.contains("+")) {
                        toSolve(testToSolve,"+",true);
                    }

                    else {
                        backgroundSequence = backgroundSequence.replace(backgroundSequence.substring(indexInnermostOParenthesis,indexInnermostCParenthesis+1),testToSolve);
                        System.out.println("This was chosen");
                    }

                    determineOps = 0;
                }
                while (backgroundSequence.contains("+") || backgroundSequence.contains("x") || backgroundSequence.contains("/") || backgroundSequence.contains("%")) {
                    String testToSolve = backgroundSequence;
                    System.out.println("Sequence now solving (1): " + testToSolve);


                    if (testToSolve.contains("x") || testToSolve.contains("/") || testToSolve.contains("%")) {
                        if (testToSolve.contains("x")) {
                            determineOps = determineOps + 2;
                        }

                        if (testToSolve.contains("/")) {
                            determineOps = determineOps + 3;
                        }

                        if (testToSolve.contains("%")) {
                            determineOps = determineOps + 4;
                        }
                        System.out.println("Current value of determine ops: " + determineOps);
                        switch (determineOps) {
                            case 2:
                                toSolve(testToSolve,"x",false);
                                break;
                            case 3:
                                toSolve(testToSolve,"/",false);
                                break;
                            case 4:
                                toSolve(testToSolve,"%",false);
                                break;
                            case 5:
                                if (testToSolve.indexOf("x") < testToSolve.indexOf("/")) {
                                    toSolve(testToSolve,"x",false);
                                }
                                if (testToSolve.indexOf("/") < testToSolve.indexOf("x")) {
                                    toSolve(testToSolve,"/",false);
                                }
                                break;
                            case 6:
                                if (testToSolve.indexOf("x") < testToSolve.indexOf("%")) {
                                    toSolve(testToSolve,"x",false);
                                }
                                if (testToSolve.indexOf("%") < testToSolve.indexOf("x")) {
                                    toSolve(testToSolve,"%",false);
                                }
                                break;
                            case 7:
                                if (testToSolve.indexOf("/") < testToSolve.indexOf("%")) {
                                    toSolve(testToSolve,"/",false);
                                }
                                if (testToSolve.indexOf("%") < testToSolve.indexOf("/")) {
                                    toSolve(testToSolve,"%",false);
                                }
                                break;
                            case 9:
                                if (testToSolve.indexOf("x") < testToSolve.indexOf("%") && testToSolve.indexOf("x") < testToSolve.indexOf("/")) {
                                    toSolve(testToSolve,"x",false);
                                }
                                if (testToSolve.indexOf("%") < testToSolve.indexOf("x") && testToSolve.indexOf("%") < testToSolve.indexOf("/")) {
                                    toSolve(testToSolve,"%",false);
                                }
                                if (testToSolve.indexOf("/") < testToSolve.indexOf("x") && testToSolve.indexOf("/") < testToSolve.indexOf("%")) {
                                    toSolve(testToSolve,"/",false);
                                }
                                break;
                        }
                    }


                    else if (testToSolve.contains("+")) {
                        toSolve(testToSolve,"+",false);
                    }
                    determineOps = 0;
                }
                }
            else {
                while (backgroundSequence.contains("+") || backgroundSequence.contains("x") || backgroundSequence.contains("/") || backgroundSequence.contains("%")) {
                    String testToSolve = backgroundSequence;
                    System.out.println("Sequence now solving (2): " + testToSolve);


                    if (testToSolve.contains("x") || testToSolve.contains("/") || testToSolve.contains("%")) {
                        if (testToSolve.contains("x")) {
                            determineOps = determineOps + 2;
                        }

                        if (testToSolve.contains("/")) {
                            determineOps = determineOps + 3;
                        }

                        if (testToSolve.contains("%")) {
                            determineOps = determineOps + 4;
                        }
                        System.out.println("Current value of determine ops: " + determineOps);
                        switch (determineOps) {
                            case 2:
                                toSolve(testToSolve,"x",false);
                                break;
                            case 3:
                                toSolve(testToSolve,"/",false);
                                break;
                            case 4:
                                toSolve(testToSolve,"%",false);
                                break;
                            case 5:
                                if (testToSolve.indexOf("x") < testToSolve.indexOf("/")) {
                                    toSolve(testToSolve,"x",false);
                                }
                                if (testToSolve.indexOf("/") < testToSolve.indexOf("x")) {
                                    toSolve(testToSolve,"/",false);
                                }
                                break;
                            case 6:
                                if (testToSolve.indexOf("x") < testToSolve.indexOf("%")) {
                                    toSolve(testToSolve,"x",false);
                                }
                                if (testToSolve.indexOf("%") < testToSolve.indexOf("x")) {
                                    toSolve(testToSolve,"%",false);
                                }
                                break;
                            case 7:
                                if (testToSolve.indexOf("/") < testToSolve.indexOf("%")) {
                                    toSolve(testToSolve,"/",false);
                                }
                                if (testToSolve.indexOf("%") < testToSolve.indexOf("/")) {
                                    toSolve(testToSolve,"%",false);
                                }
                                break;
                            case 9:
                                if (testToSolve.indexOf("x") < testToSolve.indexOf("%") && testToSolve.indexOf("x") < testToSolve.indexOf("/")) {
                                    toSolve(testToSolve,"x",false);
                                }
                                if (testToSolve.indexOf("%") < testToSolve.indexOf("x") && testToSolve.indexOf("%") < testToSolve.indexOf("/")) {
                                    toSolve(testToSolve,"%",false);
                                }
                                if (testToSolve.indexOf("/") < testToSolve.indexOf("x") && testToSolve.indexOf("/") < testToSolve.indexOf("%")) {
                                    toSolve(testToSolve,"/",false);
                                }
                                break;
                        }
                    }


                    else if (testToSolve.contains("+")) {
                        toSolve(testToSolve,"+",false);
                    }
                    determineOps = 0;
                }
            }
        }
        else {
            Toast.makeText(this,"Grouping Error; please check amount of parentheses",Toast.LENGTH_LONG).show();
        }

    }

    public void toClear() {
        String testString = txtViewSmall.getText().toString();
        if (!testString.trim().equals("")) {
           endCharIndex = txtViewSmall.getText().toString().length() - 1;
           String tempString = txtViewSmall.getText().toString().substring(0, endCharIndex);
           if (txtViewSmall.getText().toString().charAt(endCharIndex) == '(') {
               OPCount--;
           }
           if (txtViewSmall.getText().toString().charAt(endCharIndex) == ')') {
               CPCount--;
           }
           txtViewSmall.setText(tempString);
           backgroundSequence = tempString;
           Toast.makeText(this, "clear in process", Toast.LENGTH_SHORT).show();
           System.out.println("This is the old String:" + tempString);
        }
        else {
            txtViewBig.setText("0".trim());
        }
    }

    public void toCheckParenthesis() {
        if (!hasOpeningParenthesis) {
            if (txtViewSmall.getText().toString().endsWith("+")
                    || txtViewSmall.getText().toString().endsWith("-")
                    || txtViewSmall.getText().toString().endsWith("x")
                    || txtViewSmall.getText().toString().endsWith("/")
                    || txtViewSmall.getText().toString().endsWith("(")
                    || txtViewSmall.getText().toString().isEmpty()) {
                txtViewSmall.setText(txtViewSmall.getText()+"(");
                backgroundSequence = backgroundSequence.trim() + "(";
            }
            else {
                txtViewSmall.setText(txtViewSmall.getText()+"x(");
                backgroundSequence = backgroundSequence.trim() + "x(";
            }
            OPCount++;
            hasOpeningParenthesis = true;
        }
        else {
            if (txtViewSmall.getText().toString().endsWith("+")
                    || txtViewSmall.getText().toString().endsWith("-")
                    || txtViewSmall.getText().toString().endsWith("x")
                    || txtViewSmall.getText().toString().endsWith("/")
                    || txtViewSmall.getText().toString().endsWith("(")
                    || txtViewSmall.getText().toString().isEmpty()) {
                txtViewSmall.setText(txtViewSmall.getText()+"(");
                backgroundSequence = backgroundSequence.trim() + "(";
                OPCount++;
                hasOpeningParenthesis = true;
            }
            else {
                txtViewSmall.setText(txtViewSmall.getText()+")");
                backgroundSequence = backgroundSequence.trim() + ")";
                CPCount++;
                if (txtViewSmall.getText().toString().endsWith(")")) {
                    if (OPCount == CPCount) {
                        hasOpeningParenthesis = false;
                    }
                    else {
                        hasOpeningParenthesis = true;
                    }
                }
                else {
                    hasOpeningParenthesis = false;
                }
            }

        }
    }

    public void toInputChar(String s) {
        if (txtViewSmall.getText().toString().endsWith(")")) {
            txtViewSmall.setText(txtViewSmall.getText() + "x" + s);
            backgroundSequence = backgroundSequence.trim() + "x" + s;
        }
        else {
            txtViewSmall.setText(txtViewSmall.getText() + s);
            backgroundSequence = backgroundSequence.trim() + s;
        }

    }


    public void toSolve(String testToSolve, String op, boolean hasParenthesis) {

        //-- get indexes
        //get current operation
        int operandIndex = testToSolve.indexOf(op);
        System.out.println("Last index of " + op + ": " + operandIndex);

        //check operation/s at left of current operation
        if (testToSolve.contains("+") || testToSolve.contains("x") || testToSolve.contains("/") || testToSolve.contains("%")) {
            for (int i=0;i<operandIndex;i++) {
                if (testToSolve.charAt(i) == '+' || testToSolve.charAt(i) == 'x' || testToSolve.charAt(i) == '/' || testToSolve.charAt(i) == '%') {
                    lastIndex = i;
                }
            }
        }
        int beforeOperandIndex = lastIndex;
        System.out.println("Index of possible op before current opIndex: " + beforeOperandIndex);

        //check operation/s at right of current operation
        if (testToSolve.contains("+") || testToSolve.contains("x") || testToSolve.contains("/") || testToSolve.contains("%")) {
            for (int i=testToSolve.length()-1;i>operandIndex;i--) {
                if (testToSolve.charAt(i) == '+' || testToSolve.charAt(i) == 'x' || testToSolve.charAt(i) == '/') {
                    nextIndex = i;
                }
            }
        }
        int afterOperandIndex = nextIndex;
        System.out.println("Index of possible op before after opIndex: " + afterOperandIndex);
        //--

        if (hasParenthesis) {
            System.out.println("Solving under parenthesis");
            //solving w/ respect to the indexes
            if (beforeOperandIndex != -1) { //if left operand exists
                System.out.println("Solving under parenthesis, left operand exists");
                float a = Float.parseFloat(testToSolve.substring(beforeOperandIndex+1,operandIndex));
                float b;
                String newEquation = testToSolve.substring(0,beforeOperandIndex+1).trim();
                String newEquation2;
                if (afterOperandIndex != -1) { //if right operand exists
                    System.out.println("Solving under parenthesis, left operand exists, right operand exists");
                    b = Float.parseFloat(testToSolve.substring(operandIndex+1,afterOperandIndex));
                    switch (op) {
                        case "+":
                            isSolved = String.valueOf(a+b);
                            break;
                        case "x":
                            isSolved = String.valueOf(a*b);
                            break;
                        case "/":
                            isSolved = String.valueOf(a/b);
                            break;
                        case "%":
                            isSolved = String.valueOf(a%b);
                            break;
                    }
                    newEquation2 = testToSolve.substring(afterOperandIndex).trim();
                    System.out.println("This is the new equation (with groupings): " + newEquation + "()" + newEquation2);
                    System.out.println("This is the new equation plus solved: " + newEquation + isSolved + newEquation2);
                    String currentSolved = newEquation + isSolved + newEquation2;
                    if (currentSolved.contains("+") || currentSolved.contains("x") || currentSolved.contains("/") || currentSolved.contains("%")) {
                        backgroundSequence = backgroundSequence.replace(backgroundSequence.substring(indexInnermostOParenthesis+1,indexInnermostCParenthesis),currentSolved);
                        System.out.println("Current equation: " + backgroundSequence);
                    }
                    else {
                        backgroundSequence = backgroundSequence.replace(backgroundSequence.substring(indexInnermostOParenthesis,indexInnermostCParenthesis+1),currentSolved);
                        System.out.println("Current equation: " + backgroundSequence);
                    }

                }
                else { //if right operand does not exist
                    System.out.println("Solving under parenthesis, left operand exists, right operand does not exist");
                    b = Float.parseFloat(testToSolve.substring(operandIndex+1));
                    switch (op) {
                        case "+":
                            isSolved = String.valueOf(a+b);
                            break;
                        case "x":
                            isSolved = String.valueOf(a*b);
                            break;
                        case "/":
                            isSolved = String.valueOf(a/b);
                            break;
                        case "%":
                            isSolved = String.valueOf(a%b);
                            break;
                    }
                    System.out.println("This is the new equation: " + newEquation);
                    System.out.println("This is the new equation plus solved: " + newEquation + isSolved);
                    String currentSolved = newEquation + isSolved;
                    if (currentSolved.contains("+") || currentSolved.contains("x") || currentSolved.contains("/") || currentSolved.contains("%")) {
                        backgroundSequence = backgroundSequence.replace(backgroundSequence.substring(indexInnermostOParenthesis+1,indexInnermostCParenthesis),currentSolved);
                        System.out.println("Current equation: " + backgroundSequence);
                    }
                    else {
                        backgroundSequence = backgroundSequence.replace(backgroundSequence.substring(indexInnermostOParenthesis,indexInnermostCParenthesis+1),currentSolved);
                        System.out.println("Current equation: " + backgroundSequence);
                    }
                }
            }
            else { //if left operand does not exist
                System.out.println("Solving under parenthesis, left operand does not exist");
                float a = Float.parseFloat(testToSolve.substring(0,operandIndex));
                float b;
                String newEquation2;
                if (afterOperandIndex != -1) { //if right operand exists
                    System.out.println("Solving under parenthesis, left operand does not exist, right operand exists");
                    b = Float.parseFloat(testToSolve.substring(operandIndex+1,afterOperandIndex));
                    switch (op) {
                        case "+":
                            isSolved = String.valueOf(a+b);
                            break;
                        case "x":
                            isSolved = String.valueOf(a*b);
                            break;
                        case "/":
                            isSolved = String.valueOf(a/b);
                            break;
                        case "%":
                            isSolved = String.valueOf(a%b);
                            break;
                    }
                    newEquation2 = testToSolve.substring(afterOperandIndex).trim();
                    System.out.println("This is the new equation (no groupings): " + "()" + newEquation2);
                    System.out.println("This is the new equation plus solved: " + isSolved + newEquation2);
                    String currentSolved = isSolved + newEquation2;
                    if (currentSolved.contains("+") || currentSolved.contains("x") || currentSolved.contains("/") || currentSolved.contains("%")) {
                        backgroundSequence = backgroundSequence.replace(backgroundSequence.substring(indexInnermostOParenthesis+1,indexInnermostCParenthesis),currentSolved);
                        System.out.println("Current equation: " + backgroundSequence);
                    }
                    else {
                        backgroundSequence = backgroundSequence.replace(backgroundSequence.substring(indexInnermostOParenthesis,indexInnermostCParenthesis+1),currentSolved);
                        System.out.println("Current equation: " + backgroundSequence);
                    }

                }
                else { //if right operand does not exist
                    System.out.println("Solving under parenthesis, left operand does not exist, right operand does not exist");
                    b = Float.parseFloat(testToSolve.substring(operandIndex+1));
                    switch (op) {
                        case "+":
                            isSolved = String.valueOf(a+b);
                            break;
                        case "x":
                            isSolved = String.valueOf(a*b);
                            break;
                        case "/":
                            isSolved = String.valueOf(a/b);
                            break;
                        case "%":
                            isSolved = String.valueOf(a%b);
                            break;
                    }
                    System.out.println("This is the new equation plus solved: " + isSolved);
                    String currentSolved = isSolved;
                    if (currentSolved.contains("+") || currentSolved.contains("x") || currentSolved.contains("/") || currentSolved.contains("%")) {
                        backgroundSequence = backgroundSequence.replace(backgroundSequence.substring(indexInnermostOParenthesis+1,indexInnermostCParenthesis),currentSolved);
                        System.out.println("Current equation: " + backgroundSequence);
                    }
                    else {
                        backgroundSequence = backgroundSequence.replace(backgroundSequence.substring(indexInnermostOParenthesis,indexInnermostCParenthesis+1),currentSolved);
                        System.out.println("Current equation: " + backgroundSequence);
                    }
                }
            }

        }


        else {
            System.out.println("Solving NOT under parenthesis");
            //solving w/ respect to the indexes
            if (beforeOperandIndex != -1) { //if left operand exists
                System.out.println("Solving NOT under parenthesis, left operand exists");
                float a = Float.parseFloat(testToSolve.substring(beforeOperandIndex+1,operandIndex));
                float b;
                String newEquation = backgroundSequence.substring(0,beforeOperandIndex+1).trim();
                String newEquation2;
                if (afterOperandIndex != -1) { //if right operand exists
                    System.out.println("Solving NOT under parenthesis, left operand exists, right operand exists");
                    b = Float.parseFloat(testToSolve.substring(operandIndex+1,afterOperandIndex));
                    switch (op) {
                        case "+":
                            isSolved = String.valueOf(a+b);
                            break;
                        case "x":
                            isSolved = String.valueOf(a*b);
                            break;
                        case "/":
                            isSolved = String.valueOf(a/b);
                            break;
                        case "%":
                            isSolved = String.valueOf(a%b);
                            break;
                    }
                    newEquation2 = backgroundSequence.substring(afterOperandIndex).trim();
                    System.out.println("This is the new equation (no groupings): " + newEquation + "()" + newEquation2);
                    System.out.println("This is the new equation plus solved: " + newEquation + isSolved + newEquation2);
                    backgroundSequence = newEquation + isSolved + newEquation2;
                }
                else { //if right operand does not exist
                    System.out.println("Solving NOT under parenthesis, left operand exists, right operand does not exist");
                    b = Float.parseFloat(testToSolve.substring(operandIndex+1));
                    switch (op) {
                        case "+":
                            isSolved = String.valueOf(a+b);
                            break;
                        case "x":
                            isSolved = String.valueOf(a*b);
                            break;
                        case "/":
                            isSolved = String.valueOf(a/b);
                            break;
                        case "%":
                            isSolved = String.valueOf(a%b);
                            break;
                    }
                    System.out.println("This is the new equation (no groupings): " + newEquation);
                    System.out.println("This is the new equation plus solved: " + newEquation + isSolved);
                    backgroundSequence = newEquation + isSolved;
                }
            }
            else { //if left operand does not exist
                System.out.println("Solving NOT under parenthesis, left operand does not exist");
                float a = Float.parseFloat(testToSolve.substring(0,operandIndex));
                float b;
                String newEquation2;
                if (afterOperandIndex != -1) { //if right operand exists
                    System.out.println("Solving NOT under parenthesis, left operand does not exist, right operand exists");
                    b = Float.parseFloat(testToSolve.substring(operandIndex+1,afterOperandIndex));
                    switch (op) {
                        case "+":
                            isSolved = String.valueOf(a+b);
                            break;
                        case "x":
                            isSolved = String.valueOf(a*b);
                            break;
                        case "/":
                            isSolved = String.valueOf(a/b);
                            break;
                        case "%":
                            isSolved = String.valueOf(a%b);
                            break;
                    }
                    newEquation2 = backgroundSequence.substring(afterOperandIndex).trim();
                    System.out.println("This is the new equation (no groupings): " + "()" + newEquation2);
                    System.out.println("This is the new equation plus solved: " + isSolved + newEquation2);
                    backgroundSequence = isSolved + newEquation2;
                }
                else { //if right operand does not exist
                    System.out.println("Solving NOT under parenthesis, left operand does not exist, right operand does not exist");
                    b = Float.parseFloat(testToSolve.substring(operandIndex+1));
                    switch (op) {
                        case "+":
                            isSolved = String.valueOf(a+b);
                            break;
                        case "x":
                            isSolved = String.valueOf(a*b);
                            break;
                        case "/":
                            isSolved = String.valueOf(a/b);
                            break;
                        case "%":
                            isSolved = String.valueOf(a%b);
                            break;
                    }
                    System.out.println("This is the new equation plus solved: " + isSolved);
                    backgroundSequence = isSolved;
                }
            }

        }
        lastIndex = -1;
        nextIndex = -1;

        if (!backgroundSequence.contains("+") && !backgroundSequence.contains("x") && !backgroundSequence.contains("/") && !backgroundSequence.contains("%")) {
            if (backgroundSequence.contains(".")) {
                int dotCheck = backgroundSequence.indexOf(".");
                String decimalValue = backgroundSequence.substring(dotCheck+1);
                if (Integer.parseInt(decimalValue) == 0) {
                    backgroundSequence = backgroundSequence.substring(0,dotCheck);
                }
            }
        }
        txtViewBig.setText(backgroundSequence);
        System.out.println(txtViewBig.getText());

    }


}