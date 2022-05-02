package uqac.dim.soundsgood;

import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import androidx.activity.result.ActivityResult;

import java.util.HashMap;

public class SoundPlayer {

    public static float islow = 0.5f;
    public static float ismid = 1f;
    public static float ishigh = 2f;

    public static float isDo = 1f;
    public static float isDoDiese = 1.08f;
    public static float isRe = 1.16f;
    public static float isReDiese = 1.25f;
    public static float isMi = 1.33f;
    public static float isFa = 1.41f;
    public static float isFaDiese = 1.5f;
    public static float isSol = 1.58f;
    public static float isSolDiese = 1.66f;
    public static float isLa = 1.75f;
    public static float isLaDiese = 1.83f;
    public static float isSi = 1.91f;

    private SoundPool soundpool;
    private HashMap<Integer, Integer> soundsMap;
    private int piano, guitare, claves;
    private int instrument = piano;

    private boolean hasLoaded = false;


    public SoundPlayer(Context context) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            AudioAttributes audioattributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            soundpool = new SoundPool.Builder()
                    .setMaxStreams(30)
                    .setAudioAttributes(audioattributes)
                    .build();
        }
        else{
            soundpool = new SoundPool(30, AudioManager.STREAM_MUSIC, 100);
        }


        piano = soundpool.load(context,R.raw.piano_do, 1);
        guitare = soundpool.load(context,R.raw.guitare_do, 1);
        claves = soundpool.load(context,R.raw.claves, 1);

        soundpool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                hasLoaded = true;
            }
        });

    }


    public void ChangeInstrument() {

    }

    public void PlayNote(String instrumentnom, String note, int hauteur) {

        if (!hasLoaded)
            return;

        float rate = 1f;

        switch(instrumentnom)
        {
            case "piano":
                instrument = piano;
                break;

            case "guitare":
                instrument = guitare;
                break;

            case "claves":
                instrument = claves;
                break;
        }

        switch(hauteur)
        {
            case 1:
                rate = rate*islow;
                break;

            case 2:
                rate = rate*ismid;
                break;

            case 3:
                rate = rate*ishigh;
                break;
        }

        switch(note)
        {
            case "c":
                rate = rate*isDo;
                break;

            case "cDiese":
                rate = rate*isDoDiese;
                break;

            case "d":
                rate = rate*isRe;
                break;

            case "dDiese":
                rate = rate*isReDiese;
                break;

            case "e":
                rate = rate*isMi;
                break;

            case "f":
                rate = rate*isFa;
                break;

            case "fDiese":
                rate = rate*isFaDiese;
                break;

            case "g":
                rate = rate*isSol;
                break;

            case "gDiese":
                rate = rate*isSolDiese;
                break;

            case "a":
                rate = rate*isLa;
                break;

            case "aDiese":
                rate = rate*isLaDiese;
                break;

            case "b":
                rate = rate*isSi;
                break;
        }

        soundpool.play(instrument,1,1,0,0,rate);

    }

    public boolean getHasLoaded(){
        return hasLoaded;
    }
}
