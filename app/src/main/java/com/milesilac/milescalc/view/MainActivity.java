package com.milesilac.milescalc.view;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.milesilac.milescalc.InputLogic;
import com.milesilac.milescalc.R;
import com.milesilac.milescalc.ThreadManager;
import com.milesilac.milescalc.common.CalculatorContract;


public class MainActivity extends AppCompatActivity implements CalculatorContract.View {

    private CalculatorContract.InputLogic inputLogic;
    private CalculatorContract.ThreadManager threadManager;

    private TextView txtViewSmall,txtViewBig;
    private final Button[] buttons = new Button[20];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtViewSmall = findViewById(R.id.txtViewSmall);
        txtViewBig = findViewById(R.id.txtViewBig);

        buttons[0] = findViewById(R.id.btn0);
        buttons[1] = findViewById(R.id.btn1);
        buttons[2] = findViewById(R.id.btn2);
        buttons[3] = findViewById(R.id.btn3);
        buttons[4] = findViewById(R.id.btn4);
        buttons[5] = findViewById(R.id.btn5);
        buttons[6] = findViewById(R.id.btn6);
        buttons[7] = findViewById(R.id.btn7);
        buttons[8] = findViewById(R.id.btn8);
        buttons[9] = findViewById(R.id.btn9);
        buttons[10] = findViewById(R.id.btnAdd);
        buttons[11] = findViewById(R.id.btnSubt);
        buttons[12] = findViewById(R.id.btnMult);
        buttons[13] = findViewById(R.id.btnDiv);
        buttons[14] = findViewById(R.id.btnPercent);
        buttons[15] = findViewById(R.id.btnDot);
        buttons[16] = findViewById(R.id.btnParenthesis);
        buttons[17] = findViewById(R.id.btnEquals);
        buttons[18] = findViewById(R.id.btnC);
        buttons[19] = findViewById(R.id.btnQuit);

        inputLogic = new InputLogic();
        threadManager = new ThreadManager();


        for (int i=0;i<10;i++) {
            int currentNum = i;
            buttons[i].setOnClickListener(v -> {
                inputLogic.inputNum(String.valueOf(currentNum));
                System.out.println(inputLogic.readCurrentString());
                txtViewSmall.setText(inputLogic.readCurrentString());
            });
        }

        for (int i=10;i<20;i++) {
            int currentOp = i;
            buttons[i].setOnClickListener(v -> {
                switch (currentOp) {
                    case 10:
                        inputLogic.inputOp("+");
                        System.out.println(inputLogic.readCurrentString());
                        txtViewSmall.setText(inputLogic.readCurrentString());
                        break;
                    case 11:
                        inputLogic.inputOp("-");
                        System.out.println(inputLogic.readCurrentString());
                        txtViewSmall.setText(inputLogic.readCurrentString());
                        break;
                    case 12:
                        inputLogic.inputOp("×");
                        System.out.println(inputLogic.readCurrentString());
                        txtViewSmall.setText(inputLogic.readCurrentString());
                        break;
                    case 13:
                        inputLogic.inputOp("÷");
                        System.out.println(inputLogic.readCurrentString());
                        txtViewSmall.setText(inputLogic.readCurrentString());
                        break;
                    case 14:
                        inputLogic.inputOp("%");
                        System.out.println(inputLogic.readCurrentString());
                        txtViewSmall.setText(inputLogic.readCurrentString());
                        break;
                    case 15:
                        inputLogic.inputOp(".");
                        System.out.println(inputLogic.readCurrentString());
                        txtViewSmall.setText(inputLogic.readCurrentString());
                        break;
                    case 16:
//                        toCheckParenthesis();
                        inputLogic.inputOp("parentheses");
                        System.out.println(inputLogic.readCurrentString());
                        txtViewSmall.setText(inputLogic.readCurrentString());
                        break;
                    case 17:
                        threadManager.execute(inputLogic.readActualCurrentStringToSolve(), s -> txtViewBig.setText(s));
                        break;
                    case 18:
                        inputLogic.deleteLastChar();
                        System.out.println(inputLogic.readCurrentString());
                        txtViewSmall.setText(inputLogic.readCurrentString());
                        if (txtViewSmall.getText().equals("")) {
                            txtViewBig.setText("0");
                        }
                        break;
                    case 19:
                        finish();
                        break;
                }
            }); //onClickListener
        }

        buttons[18].setOnLongClickListener(v -> {
            while (!inputLogic.readCurrentString().isEmpty()) {
                inputLogic.deleteLastChar();
                if (txtViewSmall.getText().equals("")) {
                    txtViewBig.setText("0");
                }
            }
            return false;
        });

    }
}