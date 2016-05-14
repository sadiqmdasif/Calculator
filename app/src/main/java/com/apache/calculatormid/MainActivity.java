package com.apache.calculatormid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Stack;


public class MainActivity extends Activity implements View.OnClickListener {


    Button btnDivi, btnMulti, btnAdd, btnSub, btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7,
            btn8, btn9, btnEqual, btnDot, btnPercentage, btnRoot, btnInverse, btnPosNeg, btnDEL, btnCE, btnC, btnMC, btnMS, btnMR, btnMPlus, btnMMinus, btnHistory;



    TextView userInputText;
    TextView memoryStatText;

    Stack<String> mInputStack;
    Stack<String> mOperationStack;


    TextView mStackText;
    boolean resetInput = false;
    boolean hasFinalResult = false;

    String mDecimalSeperator;
    double memoryValue = Double.NaN;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss");
    String currentDateandTime = sdf.format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DecimalFormat currencyFormatter = (DecimalFormat) NumberFormat
                .getInstance();
        char decimalSeperator = currencyFormatter.getDecimalFormatSymbols()
                .getDecimalSeparator();
        mDecimalSeperator = Character.toString(decimalSeperator);

        mInputStack = new Stack<String>();
        mOperationStack = new Stack<String>();


        userInputText = (TextView) findViewById(R.id.txtInput);
        userInputText.setText("0");

        memoryStatText = (TextView) findViewById(R.id.txtMemory);
        memoryStatText.setText("");

        mStackText = (TextView) findViewById(R.id.txtStack);


        /*operator buttons */
        btnAdd = (Button) findViewById(R.id.buttonAdd);
        btnSub = (Button) findViewById(R.id.buttonSub);
        btnDivi = (Button) findViewById(R.id.buttonDivi);
        btnMulti = (Button) findViewById(R.id.buttonMulti);
        btnEqual = (Button) findViewById(R.id.buttonEqual);
        btnPercentage = (Button) findViewById(R.id.buttonPercentage);
        btnDot = (Button) findViewById(R.id.buttonDot);
        btnRoot = (Button) findViewById(R.id.buttonRoot);
        btnInverse = (Button) findViewById(R.id.buttonInv);
        btnPosNeg = (Button) findViewById(R.id.buttonPosNeg);

        /*numeric buttons*/
        btn0 = (Button) findViewById(R.id.button0);
        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button3);
        btn4 = (Button) findViewById(R.id.button4);
        btn5 = (Button) findViewById(R.id.button5);
        btn6 = (Button) findViewById(R.id.button6);
        btn7 = (Button) findViewById(R.id.button7);
        btn8 = (Button) findViewById(R.id.button8);
        btn9 = (Button) findViewById(R.id.button9);

        /*memory buttons*/
        btnMC = (Button) findViewById(R.id.buttonMC);
        btnMR = (Button) findViewById(R.id.buttonMR);
        btnMS = (Button) findViewById(R.id.buttonMS);
        btnMPlus = (Button) findViewById(R.id.buttonMPLUS);
        btnMMinus = (Button) findViewById(R.id.buttonMMinus);
        btnC = (Button) findViewById(R.id.buttonC);
        btnCE = (Button) findViewById(R.id.buttonCE);
        btnDEL = (Button) findViewById(R.id.buttonDel);

        btnHistory = (Button) findViewById(R.id.buttonHistory);


        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btnAdd.setOnClickListener(this);

        btnSub.setOnClickListener(this);
        btnMulti.setOnClickListener(this);
        btnDivi.setOnClickListener(this);
        btnPercentage.setOnClickListener(this);
        btnEqual.setOnClickListener(this);
        btnRoot.setOnClickListener(this);
        btnPosNeg.setOnClickListener(this);
        btnInverse.setOnClickListener(this);
        btnDot.setOnClickListener(this);

        btnMC.setOnClickListener(this);
        btnMR.setOnClickListener(this);
        btnMS.setOnClickListener(this);
        btnMPlus.setOnClickListener(this);
        btnMMinus.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnCE.setOnClickListener(this);
        btnDEL.setOnClickListener(this);

        btnHistory.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

               try {
                   Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                   startActivity(intent);
               }
               catch (Exception e){}

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Button btn = (Button) v;

        String buttonPressed = (String) btn.getText();

        ProcessKeypadInput(buttonPressed);

    }


    private void writeToHistory(int a) {

        String inputTxt;
        File path = getExternalFilesDir(null);
        final File file = new File(path, "calculationHistory.txt");
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(file, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            try {
                if (a == 1) {
                    inputTxt = "DATE:" + currentDateandTime + "\n" + String.valueOf(mStackText.getText()) + userInputText.getText().toString();
                } else {
                    inputTxt = " = " + userInputText.getText().toString() + "\n";
                }
                stream.write(inputTxt.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void ProcessKeypadInput(String buttonPressed) {

        String text = buttonPressed;
        String currentInput = userInputText.getText().toString();

        int currentInputLen = currentInput.length();
        String evalResult = null;
        double userInputValue = Double.NaN;

        switch (buttonPressed) {


            case "DEL":
                if (resetInput)
                    return;

                int endIndex = currentInputLen - 1;
                //reset input to 0
                if (endIndex < 1) {
                    userInputText.setText("0");
                }
                // Trim last character
                else {
                    userInputText.setText(currentInput.subSequence(0, endIndex));
                }
                break;
            case "±":

                if (currentInputLen > 0 && currentInput != "0") {
                    // Already has -
                    if (currentInput.charAt(0) == '-') {
                        userInputText.setText(currentInput.subSequence(1,
                                currentInputLen));
                    }
                    // - sign
                    else {
                        userInputText.setText("-" + currentInput.toString());
                    }
                }
                break;
            case "CE":
                userInputText.setText("0");
                break;
            case "C":
                userInputText.setText("0");
                clearStacks();
                break;
            case ".":
                if (hasFinalResult || resetInput) {
                    userInputText.setText("0" + mDecimalSeperator);
                    hasFinalResult = false;
                    resetInput = false;
                } else if (currentInput.contains("."))
                    return;
                else
                    userInputText.append(mDecimalSeperator);
                break;
            case "÷":
            case "+":
            case "-":
            case "X":
                if (resetInput) {
                    mInputStack.pop();
                    mOperationStack.pop();
                } else {
                    if (currentInput.charAt(0) == '-') {
                        mInputStack.add("(" + currentInput + ")");
                    } else {
                        mInputStack.add(currentInput);
                    }
                    mOperationStack.add(currentInput);
                }

                mInputStack.add(text);
                mOperationStack.add(text);

                dumpInputStack();
                evalResult = evaluateResult(false);
                if (evalResult != null)
                    userInputText.setText(evalResult);

                resetInput = true;
                break;
            case "=":


                if (mOperationStack.size() == 0)
                    break;

                mOperationStack.add(currentInput);
                evalResult = evaluateResult(true);
                if (evalResult != null) {
                    writeToHistory(1);
                    userInputText.setText(evalResult);
                    resetInput = false;
                    hasFinalResult = true;
                    writeToHistory(0);
                    clearStacks();
                }

                break;
            case "M+":
                userInputValue = tryParseUserInput();
                if (Double.isNaN(userInputValue))
                    return;
                if (Double.isNaN(memoryValue))
                    memoryValue = 0;
                memoryValue += userInputValue;
                displayMemoryStat();

                hasFinalResult = true;

                break;
            case "M-":
                userInputValue = tryParseUserInput();
                if (Double.isNaN(userInputValue))
                    return;
                if (Double.isNaN(memoryValue))
                    memoryValue = 0;
                memoryValue -= userInputValue;
                displayMemoryStat();
                hasFinalResult = true;
                break;
            case "MC":
                memoryValue = Double.NaN;
                displayMemoryStat();
                break;
            case "MR":
                if (Double.isNaN(memoryValue))
                    return;
                userInputText.setText(doubleToString(memoryValue));
                displayMemoryStat();
                break;
            case "MS":
                userInputValue = tryParseUserInput();
                if (Double.isNaN(userInputValue))
                    return;
                memoryValue = userInputValue;
                displayMemoryStat();
                hasFinalResult = true;
                break;
            default:
                if (Character.isDigit(text.charAt(0))) {
                    if (currentInput.equals("0") || resetInput || hasFinalResult) {
                        userInputText.setText(text);
                        resetInput = false;
                        hasFinalResult = false;
                    } else {
                        userInputText.append(text);
                        resetInput = false;
                    }

                }
                break;

        }

    }

    private void clearStacks() {
        mInputStack.clear();
        mOperationStack.clear();
        mStackText.setText("");
    }

    private void dumpInputStack() {
        Iterator<String> it = mInputStack.iterator();
        StringBuilder sb = new StringBuilder();

        while (it.hasNext()) {
            CharSequence iValue = it.next();
            sb.append(iValue);

        }

        mStackText.setText(sb.toString());
    }

    private String evaluateResult(boolean requestedByUser) {
        if ((!requestedByUser && mOperationStack.size() != 4)
                || (requestedByUser && mOperationStack.size() != 3))
            return null;

        String left = mOperationStack.get(0);
        String operator = mOperationStack.get(1);
        String right = mOperationStack.get(2);
        String tmp = null;
        if (!requestedByUser)
            tmp = mOperationStack.get(3);

        double leftVal = Double.parseDouble(left.toString());
        double rightVal = Double.parseDouble(right.toString());
        double result = Double.NaN;

        if (operator.equals("÷")) {
            result = leftVal / rightVal;
        } else if (operator.equals("X")) {
            result = leftVal * rightVal;

        } else if (operator.equals("+")) {
            result = leftVal + rightVal;
        } else if (operator.equals("-")) {
            result = leftVal - rightVal;

        }

        String resultStr = doubleToString(result);
        if (resultStr == null)
            return null;

        mOperationStack.clear();
        if (!requestedByUser) {
            mOperationStack.add(resultStr);
            mOperationStack.add(tmp);
        }

        return resultStr;
    }

    private String doubleToString(double value) {
        if (Double.isNaN(value))
            return null;

        long longVal = (long) value;
        if (longVal == value)
            return Long.toString(longVal);
        else
            return Double.toString(value);

    }

    private double tryParseUserInput() {
        String inputStr = userInputText.getText().toString();
        double result = Double.NaN;
        try {
            result = Double.parseDouble(inputStr);

        } catch (NumberFormatException nfe) {
        }
        return result;

    }

    private void displayMemoryStat() {
        if (Double.isNaN(memoryValue)) {
            memoryStatText.setText("");
        } else {
            memoryStatText.setText("M = " + doubleToString(memoryValue));
        }
    }


}



