package com.example.countcustomers;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Button addbtn,removebtn;
    TextView resulttxt;

    int count = 0;
    int max = 0,sum = 0,maxh,maxm,avcount=0;
    float av;

    TextView maxtxt, maxtimetxt, averagetxt;
    LinearLayout resultBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        addbtn = findViewById(R.id.addbtn);
        removebtn = findViewById(R.id.removebtn);
        removebtn.setEnabled(false);
        resulttxt = findViewById(R.id.counttxt);
        resulttxt.setText(String.valueOf(count));

        maxtxt = findViewById(R.id.maxtxt);
        maxtimetxt = findViewById(R.id.maxtimetxt);
        averagetxt = findViewById(R.id.averagetxt);
        resultBox = findViewById(R.id.resultBox);
        resultBox.setVisibility(View.INVISIBLE);

    }

    public void addCustomer(View v){
        count++;
        if (count>0) {
            removebtn.setEnabled(true);
        }
        calculateResults();
    }

    public  void removeCustomer(View v){
        count--;
        if(count<=0){
            v.setEnabled(false); //removebtn.setEnabled(false);
        }
        calculateResults();
    }

    public void calculateResults(){
        resulttxt.setText(String.valueOf(count));

        if(count>max){
            max = count;
            maxh = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            maxm = Calendar.getInstance().get(Calendar.MINUTE);
         }

        sum+=count;
        avcount++;
    }

    public void showResutls (View v){
        av = sum/avcount;
        String message = "Max:"+String.valueOf(max)+" "+String.valueOf(maxh)+":"+String.valueOf(maxm)+
                " Average:"+String.valueOf(av);

        resultBox.setVisibility(View.VISIBLE);
        maxtxt.setText(String.valueOf(max));
        maxtimetxt.setText(String.valueOf(maxh)+":"+String.valueOf(maxm));
        averagetxt.setText(String.valueOf(av));


        //Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void clearAll(View v){
        count =0;
        avcount=0;
        max=0;
        resulttxt.setText(String.valueOf(count));
    }

    public void backMenu(View v){
        resultBox.setVisibility(View.INVISIBLE);
    }
}

