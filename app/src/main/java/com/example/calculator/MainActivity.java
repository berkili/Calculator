package com.example.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.javia.arity.Symbols;

public class MainActivity extends AppCompatActivity {
    private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnOndalik,
            btnMinus, btnMultiply, btnDivide, btnEq, btnBackspace, btnClear;
    private Button btnPlus;
    private TextView txtCurrent, txtAll;

    private Symbols symbols = new Symbols();
    private boolean process = false, isDot = false;
    private String number = "";
    private Double result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewSetup();

        View.OnClickListener listener = view -> {
            Button btn = (Button) view;
            NotProcess(process);

            if(btn.getText().equals(".") && isDot) {
                return;
            } else if(btn.getText().equals(".") && number.length() <= 0) {
                return;
            } else if(btn.getText().equals(".")) {
                return;
            }
            number += btn.getText().toString();
            txtCurrent.setText(number);
        };

        View.OnClickListener oprListener = view -> {
            Button btn = (Button) view;

            if(btn.getText().equals("C")) {
                ClearProcess();
                return;
            }
            if(number.length() <= 0) {
                return;
            }
            if(btn.getText().equals("=")) {
                try {
                    txtAll.append(number);
                    result = symbols.eval(txtAll.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                isDot = false;
                txtCurrent.setText(String.valueOf(result));
                process = false;
            }
            else {
                number += btn.getText().toString();
                txtAll.append(number);
                isDot = false;
            }
            number = "";
        };

        btnBackspace.setOnClickListener(view -> {
            handleBackspace();
        });

        btn0.setOnClickListener(listener);
        btn1.setOnClickListener(listener);
        btn2.setOnClickListener(listener);
        btn3.setOnClickListener(listener);
        btn4.setOnClickListener(listener);
        btn5.setOnClickListener(listener);
        btn6.setOnClickListener(listener);
        btn7.setOnClickListener(listener);
        btn8.setOnClickListener(listener);
        btn9.setOnClickListener(listener);
        btnOndalik.setOnClickListener(oprListener);
        btnPlus.setOnClickListener(oprListener);
        btnMultiply.setOnClickListener(oprListener);
        btnDivide.setOnClickListener(oprListener);
        btnMinus.setOnClickListener(oprListener);
        btnEq.setOnClickListener(oprListener);
        btnClear.setOnClickListener(oprListener);
    }

    private void viewSetup() {
        btn0 = (Button) findViewById(R.id.btn0);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);
        btnOndalik = (Button) findViewById(R.id.btnOndalik);
        btnPlus = (Button) findViewById(R.id.btnAdd);
        btnMinus = (Button) findViewById(R.id.btnSub);
        btnMultiply = (Button) findViewById(R.id.btnMulti);
        btnDivide = (Button) findViewById(R.id.btnDiv);
        btnEq = (Button) findViewById(R.id.btnEq);
        btnBackspace = (Button) findViewById(R.id.btnDelete);
        btnClear = (Button) findViewById(R.id.btnAC);

        txtCurrent = (TextView) findViewById(R.id.txtCurrent);
        txtAll = (TextView) findViewById(R.id.txtAll);

        txtCurrent.setText("");
        txtAll.setText("");
    }

    private void NotProcess(boolean control) {
        if(!control) {
            ClearProcess();
            process = true;
        }
    }
    private void ClearProcess() {
        txtCurrent.setText("");
        txtAll.setText("");
        number = "";
    }
    private void handleBackspace() {
        String txt = txtCurrent.getText().toString();
        if(txt.length() > 0) {
            txt = txt.substring(0, txt.length() - 1);
            if(txt.equals(""))
                txt = "";
            txtCurrent.setText(txt);
        }
        else {
            txt = txtAll.getText().toString();
            if(txt.length() > 0) {
                txt = txt.substring(0, txt.length() - 1);
                if(txt.equals(""))
                    ClearProcess();

                txtAll.setText(txt);
            }
        }
    }

    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("Result", txtCurrent.getText().toString());
        outState.putString("Process", txtAll.getText().toString());
        outState.putString("Number", number);
        outState.putBoolean("isDot", isDot);
        outState.putBoolean("isProcess", process);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        txtCurrent.setText(savedInstanceState.getString("Result"));
        txtAll.setText(savedInstanceState.getString("Process"));
        number = savedInstanceState.getString("Number");
        isDot = savedInstanceState.getBoolean("isDot");
        process = savedInstanceState.getBoolean("IsProcess");
    }
}