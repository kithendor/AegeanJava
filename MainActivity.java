package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int activePlayer = 0; //0 = player 1, 1 player 2

    int gameState[] = {5,5,5,5,5,5,5,5,5}; //5 = den exei paiksei kanenas apo tos 2
    int winStates[][] = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    LinearLayout restartLayout;
    TextView wintxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        setContentView(R.layout.activity_main);

        wintxt = findViewById(R.id.wintxt);
        restartLayout = findViewById(R.id.restartLayout);
        restartLayout.setVisibility(View.INVISIBLE);
    }


    public void tapIt(View v){
        ImageView tap = (ImageView) v;

        int tappedPlace = Integer.parseInt(tap.getTag().toString());
        //System.out.println(tap.getTag().toString());

        if(gameState[tappedPlace]==5) {
            gameState[tappedPlace]=activePlayer;
            tap.setTranslationY(-1000f);

            if (activePlayer == 0) {
                tap.setImageResource(R.drawable.x);
                activePlayer = 1;
            } else {
                tap.setImageResource(R.drawable.o);
                activePlayer = 0;
            }

            tap.animate().translationYBy(1000f).rotation(360).setDuration(250);

            for(int win[] : winStates){
                if(gameState[win[0]]==gameState[win[1]] && gameState[win[1]]==gameState[win[2]] && gameState[win[0]]!=5){
                    wintxt.setText(gameState[win[0]]+"Player Win");
                    restartLayout.setVisibility(View.VISIBLE);
                }
                else{
                    boolean isdraw = true;
                    for(int i: gameState){
                        if(i==5) isdraw = false;
                    }
                    if (isdraw){
                        wintxt.setText("Draw");
                        restartLayout.setVisibility(View.VISIBLE);
                    }
                }
            }

        }
    }

    public void restartGame(View v){
        restartLayout.setVisibility(View.INVISIBLE);
        activePlayer = 0;
        for(int i=0;i<gameState.length;i++) gameState[i]=5;

        TableLayout gameLayout = findViewById(R.id.gameLaout);

        for(int i=0;i<gameLayout.getChildCount();i++){
            TableRow tr = (TableRow) gameLayout.getChildAt(i);

            for(int j=0;j<tr.getChildCount();j++){
                ((ImageView)tr.getChildAt(j)).setImageResource(0);
            }

        }
    }
}
