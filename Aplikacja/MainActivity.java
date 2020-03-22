package com.example.kalkulatorkredytowy;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    int N, years, k, amountOfMonths;
    double r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

                EditText editText = (EditText) findViewById(R.id.liczba_rat_na_rok);
                editText.setText("12");      // Standardowa liczba rat w ciągu roku
                if(savedInstanceState != null){
                    N = savedInstanceState.getInt("N");             // Kwota kredytu
                    years = savedInstanceState.getInt("years");     // liczba lat spłaty
                    k = savedInstanceState.getInt("k");             // liczba rat w ciągu roku (standardowo 12)
                    r = savedInstanceState.getDouble("r");          // Oprocentowanie

                    setEditText(N, years, k, r);
                    getData();
                    count();
                    countCostOfCredit();
                    setValue();
                }
            }



    void setEditText(int N, int years, int k, double r){
        EditText editText = (EditText) findViewById(R.id.editTextAmount);
        editText.setText(String.valueOf(N));

        editText = (EditText) findViewById(R.id.liczba_lat);
        editText.setText(String.valueOf(years));

        editText = (EditText) findViewById(R.id.liczba_rat_na_rok);
        editText.setText(String.valueOf(k));

        editText = (EditText) findViewById(R.id.interest);
        editText.setText(String.valueOf(r));
    }

    String error;
    void getData(){
        try {
            EditText editText = (EditText) findViewById(R.id.editTextAmount);
            CharSequence buffor = editText.getText();
            N = Integer.parseInt(buffor.toString());        // Kwota kredytu

            editText = (EditText) findViewById(R.id.liczba_lat);
            CharSequence buffor1 = editText.getText();
            years = Integer.parseInt(buffor1.toString());   // liczba lat spłaty

            editText = (EditText) findViewById(R.id.liczba_rat_na_rok);
            CharSequence buffor2 = editText.getText();
            k = Integer.parseInt(buffor2.toString());       // liczba rat w ciągu roku (standardowo 12)

            editText = (EditText) findViewById(R.id.interest);
            CharSequence buffor3 = editText.getText();
            r = Double.parseDouble(buffor3.toString());     // Oprocentowanie
            amountOfMonths = years * k;
        }
        catch (Exception ex){
            error = "Nieprawdidłowe dane \n Nalzeży uzupełnić wszystkie pola";
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    double I, value;
    String valueInstallment;
    void count(){
        double partq = 1 + ((r*0.01) / k);
        I = N * Math.pow(partq, amountOfMonths)*((partq - 1) / (Math.pow(partq, amountOfMonths) - 1));

        value = I;
        value = value * 100;
        value = Math.round(value);
        value = value / 100;
        valueInstallment = String.valueOf(value);
    }

    double costOfCredit, allAmount;
    String costOfCreditStrg, allAmountStr;
    public void countCostOfCredit(){
        allAmount = I * amountOfMonths;
        allAmount = Math.round(allAmount*100);
        allAmount = allAmount/100;

        costOfCredit = allAmount - N;
        costOfCreditStrg = String.valueOf(costOfCredit);
        allAmountStr = String.valueOf(allAmount);
    }

    void setValue(){
        TextView textView = (TextView) findViewById(R.id.valueOfI);
        textView.setText(valueInstallment);

        TextView textView1 = (TextView) findViewById(R.id.valueOfAllAmount);
        textView1.setText(allAmountStr);

        textView = (TextView) findViewById(R.id.valueOfCost);
        textView.setText(costOfCreditStrg);
    }


    public void onClickCount(View view) {
        getData();
        count();
        countCostOfCredit();
        setValue();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("N", N);
        savedInstanceState.putInt("years", years);
        savedInstanceState.putInt("k", k);
        savedInstanceState.putDouble("r", r);
    }

        }

