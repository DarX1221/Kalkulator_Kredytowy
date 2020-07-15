package com.example.kalkulatorkredytowy;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            N = savedInstanceState.getLong("N");
            years = savedInstanceState.getInt("years");
            r = savedInstanceState.getDouble("r");

            setEditText(N, years, r);
            getData();
            count();
            countCostOfCredit();
            setValue();
        }
    }

    long N;
    int years, amountOfMonths;
    double r;
    int k = 12;     // liczba rat w ciągu roku

    void setEditText(long N, int years, double r){
        EditText editText = (EditText) findViewById(R.id.editTextAmount);
        editText.setText(String.valueOf(N));

        editText = (EditText) findViewById(R.id.liczba_lat);
        editText.setText(String.valueOf(years));

        editText = (EditText) findViewById(R.id.interest);
        editText.setText(String.valueOf(r));
    }

    public void onClickCount(View view) {
        if(getData()) {     // Jeżeli dane pobrane z EditText są prawidłowe, poniższe funkcje obliczą kredyt
            count();
            countCostOfCredit();
            setValue();
        }
    }

    String error = " Nieprawdidłowe dane \n Nalzeży uzupełnić wszystkie pola";
    boolean getData(){
        try {
            EditText editText = (EditText) findViewById(R.id.editTextAmount);
            CharSequence buffor = editText.getText();
            N = Long.parseLong(buffor.toString());          // Kwota kredytu
            if(buffor.equals("")) {
                throw new Exception();
            }
            if(N <= 499) {
                error = "Podaj kwotę kredytu min. 500";
                throw new IllegalArgumentException();
            }
            if(N > 2000000000) {
                error = "Kwota kredytu jest zbyt duża!";
                throw new IllegalArgumentException();
            }


            editText = (EditText) findViewById(R.id.liczba_lat);
            CharSequence buffor1 = editText.getText();
            years = Integer.parseInt(buffor1.toString());   // liczba lat spłaty
            if(years > 100) {
                error = "Liczba lat musi być mniejsza 100";
                throw new IllegalArgumentException();
            }


            editText = (EditText) findViewById(R.id.interest);
            CharSequence buffor3 = editText.getText();
            r = Double.parseDouble(buffor3.toString());     // Oprocentowanie
            if(r > 50) {
                error = "Oprocentowanie kredytu nie może być większe niż 50%";
                throw new IllegalArgumentException();
            }



            amountOfMonths = years * k;
            return true;
        }
        catch (IllegalArgumentException ex){
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
            return false;
        }
        catch (Exception ex){
            error = " Nieprawdidłowe dane \n Nalzeży uzupełnić wszystkie pola";
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
            return false;
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
        valueInstallment = String.format("%,.2f", value);
    }

    double costOfCredit, allAmount;
    String costOfCreditStrg, allAmountStr;
    public void countCostOfCredit(){
        allAmount = I * amountOfMonths;
        allAmount = Math.round(allAmount*100);
        allAmount = allAmount/100;

        costOfCredit = allAmount - N;
        costOfCredit = Math.round(costOfCredit*100);
        costOfCredit = costOfCredit/100;

        costOfCreditStrg = String.format("%,.2f", costOfCredit);
        allAmountStr = String.format("%,.2f", allAmount);
    }


    void setValue(){
        TextView textView = (TextView) findViewById(R.id.valueOfI);
        textView.setText(valueInstallment);

        TextView textView1 = (TextView) findViewById(R.id.valueOfAllAmount);
        textView1.setText(allAmountStr);

        textView = (TextView) findViewById(R.id.valueOfCost);
        textView.setText(costOfCreditStrg);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putLong("N", N);
        savedInstanceState.putInt("years", years);
        savedInstanceState.putDouble("r", r);
    }


}
