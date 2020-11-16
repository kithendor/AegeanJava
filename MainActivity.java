package com.example.volume;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mp;
    ImageButton playbtn,pausebtn,stopbtn;

    SeekBar seeksound,seeksong;

    AudioManager am;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mp = MediaPlayer.create(this,R.raw.trampoline);

        playbtn = findViewById(R.id.playbtn);
        pausebtn = findViewById(R.id.pausebtn);
        stopbtn = findViewById(R.id.stopbtn);

        seeksound = findViewById(R.id.seeksound);
        seeksong = findViewById(R.id.seeksong);

        seeksong.setMax(mp.getDuration());

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                seeksong.setProgress(mp.getCurrentPosition());
            }
        },0,100);

        seeksong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser) mp.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        am = (AudioManager) getSystemService(AUDIO_SERVICE);
        int maxvolume = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curvolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);

        seeksound.setMax(maxvolume);
        seeksound.setProgress(curvolume);

        seeksound.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                am.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    public void playSound(View v){
        mp.start();
        ImageButton ib = (ImageButton)v;
        ((ImageButton) v).setImageResource(R.drawable.playb);
        pausebtn.setImageResource(R.drawable.pausew);
        stopbtn.setImageResource(R.drawable.stopw);
    }

    public void pauseSound(View v){
        mp.pause();
        ((ImageButton) v).setImageResource(R.drawable.pauseb);
        playbtn.setImageResource(R.drawable.playw);
        stopbtn.setImageResource(R.drawable.stopw);
    }

    public void stopSound(View v){
        mp.stop();
        ((ImageButton) v).setImageResource(R.drawable.stopb);
        pausebtn.setImageResource(R.drawable.pausew);
        playbtn.setImageResource(R.drawable.playw);
        mp = MediaPlayer.create(this,R.raw.trampoline);
    }
}


