package com.example.android.musicplayer;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    /*
    DECLARATION OF COMPONENTS USED IN THE activity_main.xml
    COMPONENTS :
        MediaPlayer: To play audio or video files,
        TextView: textview to display the start time and the end time
        Button: Button for PLAY and PAUSE
        Handler: To handle and watch our audio file while we play
     */
    MediaPlayer media;
    Button playBtn, pauseBtn, resetBtn;
    TextView startTime;
    TextView endTime;
    private double start = 0;
    private double end = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playBtn = (Button) findViewById(R.id.playBtn);
        pauseBtn = (Button) findViewById(R.id.pauseBtn);
        resetBtn = (Button) findViewById(R.id.resetBtn);
        startTime = (TextView) findViewById(R.id.start_time);
        endTime = (TextView) findViewById(R.id.end_time);

        media = MediaPlayer.create(this, R.raw.song);

        /*

            Onclick event to handle when an audio file is played

        */

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                media.start();

                start = media.getCurrentPosition();

                end = media.getDuration();

                endTime.setText(String.format("%d:%d",
                        TimeUnit.MILLISECONDS.toMinutes((long) end),
                        TimeUnit.MILLISECONDS.toSeconds((long) end) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                        end)))
                );
                startTime.setText(String.format("%d:%d",
                        TimeUnit.MILLISECONDS.toMinutes((long) start),
                        TimeUnit.MILLISECONDS.toSeconds((long) start) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) start)))
                );

                handler.postDelayed(UpdateSongTime, 100);

            }
        });

        /*
            Onclick event to handle when an audio file is been paused
         */

        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                media.pause();
            }
        });

        /*
            Onclick event to handle reset of an audio file
         */

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                media.reset();


            }
        });



    }

    /*
        UPDATES THE CURRENT TIME WHEN PLAYING A MUSIC
     */

    private Runnable UpdateSongTime = new Runnable() {
        @Override
        public void run() {
            start = media.getCurrentPosition();
            startTime.setText(String.format("%d:%d",
                    TimeUnit.MILLISECONDS.toMinutes((long) start),
                    TimeUnit.MILLISECONDS.toSeconds((long) start) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) start)))
            );

            handler.postDelayed(this, 100);
        }
    };


}
