package com.abhilashvadnala.rockpaperscissor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    // declaring calculator screen, which is used for displaying inputs and results
    TextView calcScreen;

    // running number || currently entering number
    // initial input || Left hand side operand
    // second input || Right hand side operand
    String running = "";
    String lhso = "";
    String rhso = "";

    // enumerating operations
    enum Operation {
        SUM, DIFF, DIV, MUL, EQUALS
    }

    // variable to store current operation
    Operation currOp;

    // variable to store mathematical result
    double result = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mylayout);

        // initializing calculator screen and setting it to 0
        calcScreen = findViewById(R.id.screen);
        calcScreen.setText("0");

        // declaring and initializing all number buttons
        Button num0 = findViewById(R.id.num0);
        Button num1 = findViewById(R.id.num1);
        Button num2 = findViewById(R.id.num2);
        Button num3 = findViewById(R.id.num3);
        Button num4 = findViewById(R.id.num4);
        Button num5 = findViewById(R.id.num5);
        Button num6 = findViewById(R.id.num6);
        Button num7 = findViewById(R.id.num7);
        Button num8 = findViewById(R.id.num8);
        Button num9 = findViewById(R.id.num9);
        Button point = findViewById(R.id.point);

        // declaring and initializing operational buttons
        Button opAllClear = findViewById(R.id.opAllClear);
        Button opPlusOrMinus = findViewById(R.id.opPlusOrMinus);
        Button opPercentage = findViewById(R.id.opPercentage);
        Button opDiv = findViewById(R.id.opDiv);
        Button opMul = findViewById(R.id.opMul);
        Button opDiff = findViewById(R.id.opDiff);
        Button opSum = findViewById(R.id.opSum);
        Button opEquals = findViewById(R.id.opEquals);

        num0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(calcScreen.getText().equals("0")) {
                    // don't add another zero to the screen
                } else {
                    // append zero
                    numberPressed(0);
                }
            }
        });

        num1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberPressed(1);
            }
        });

        num2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberPressed(2);
            }
        });

        num3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberPressed(3);
            }
        });

        num4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberPressed(4);
            }
        });

        num5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberPressed(5);
            }
        });

        num6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberPressed(6);
            }
        });

        num7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberPressed(7);
            }
        });

        num8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberPressed(8);
            }
        });

        num9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberPressed(9);
            }
        });

        point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(calcScreen.getText().toString().contains(".")) {
                    // don't add another point in the same string.
                } else {
                    if(Double.valueOf(calcScreen.getText().toString()) == 0)
                        running = "0";
                    running += ".";
                    calcScreen.setText(running);
                }
            }
        });

        opAllClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                running = "";
                lhso = "";
                rhso = "";
                currOp = null;
                result = 0.0;
                calcScreen.setText("0");
            }
        });

        opPlusOrMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plusOrMinus();
            }
        });

        opPercentage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                percentage();
            }
        });

        opDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processOperation(Operation.DIV);
            }
        });

        opMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processOperation(Operation.MUL);
            }
        });

        opDiff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processOperation(Operation.DIFF);
            }
        });

        opSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processOperation(Operation.SUM);
            }
        });

        opEquals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                compute(Operation.EQUALS);
            }
        });
    }

    public void percentage() {
        if(!running.equals("")) {
            double temp = Double.valueOf(running);
            temp = temp / 100;
            running = String.valueOf(temp);
            calcScreen.setText(running);
        }
    }

    public void plusOrMinus() {
        if(!running.equals("") && running.charAt(0) != '-') {
            running = "-"+running;
            calcScreen.setText(running);
        }
    }

    public void  compute(Operation op) {
        if(currOp != null) {
            if(!running.equals("")) {
                result = operate(currOp, lhso, running);
                running = "";
                /*if(result % 1 == 0) {
                    result = (int)result;
                }*/
                lhso = String.valueOf(result);
            }
            calcScreen.setText(lhso);
        }
    }

    public void processOperation(Operation op) {
        if(currOp != null) {
            if(!running.equals("")) {
                rhso = running;
                running = "";

                result = operate(op,lhso,rhso);

                /*if(result % 1 == 0) {
                    result = (int)result;
                }*/

                lhso = String.valueOf(result);
            }
        } else {
            // processing the operation for the very first time when currOp was null.
            lhso = running;
            running = "";
        }

        // Updating the current operation every time.
        currOp = op;

        // Updating the result on screen every time.
        calcScreen.setText(lhso);
    }

    public double operate(Operation op, String v1, String v2) {
        switch (op) {
            case SUM:
                result = Double.valueOf(v1) + Double.valueOf(v2);
                break;
            case DIFF:
                result = Double.valueOf(v1) - Double.valueOf(v2);
                break;
            case DIV:
                if(Double.valueOf(v2) == 0) {
                    result = Double.NaN;
                }
                if(v2.equals("")){
                    result = 1;
                }
                result = Double.valueOf(v1) / Double.valueOf(v2);
                break;
            case MUL:
                result = Double.valueOf(v1) * Double.valueOf(v2);
                break;
        }
        return result;
    }

    public void numberPressed(int n) {
        running += String.valueOf(n);
        calcScreen.setText(running);
    }

}
