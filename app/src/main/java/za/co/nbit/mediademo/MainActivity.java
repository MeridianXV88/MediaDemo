package za.co.nbit.mediademo;

import android.content.Context;
import android.media.AudioManager;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.MenuItem;
import android.view.Menu;
import android.media.MediaPlayer;
import android.widget.SeekBar;
import android.media.AudioManager;

public class MainActivity extends AppCompatActivity {

    MediaPlayer myplayer;
    AudioManager audioManager;
    SeekBar trackDuration;
    Handler seekHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekHandler = new Handler();

        myplayer  = MediaPlayer.create(this, R.raw.alinabara);


        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        SeekBar VolumeControl = (SeekBar) findViewById(R.id.seekBarVol);
        VolumeControl.setMax(maxVolume);
        VolumeControl.setProgress(curVolume);


        int trackMax = myplayer.getDuration();
        //int trackCur = myplayer.getCurrentPosition();

        trackDuration = (SeekBar) findViewById(R.id.seekBarTrackProg);
        trackDuration.setMax(myplayer.getDuration());

        seekUpdate();

        trackDuration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        myplayer.seekTo(progress);
                    }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                    myplayer.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                    myplayer.start();
            }
        });


        VolumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {


            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }


    Runnable run = new Runnable()
    {
        @Override
        public void run()
        {
            seekUpdate();
        }
    };

    public void seekUpdate()
    {
        trackDuration.setProgress(myplayer.getCurrentPosition());
        seekHandler.postDelayed(run, 1000);
    }

    public void play (View view)
    {
        myplayer.start();

    }


    public void pause (View view)
    {
        myplayer.pause();
    }
}
