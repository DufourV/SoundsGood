package uqac.dim.soundsgood;

import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.Log;

import androidx.activity.result.ActivityResult;

import java.util.ArrayList;
import java.util.HashMap;

public class SoundPlayer {
    private static final float ROOT = 1.09257f;

    public static final float islow = 0.5f;
    public static final float ismid = 1.0f;
    public static final float ishigh = 2.0f;

    private SoundPool soundPool;
    private int instrument; // piano = 0, guitare = 1,  claves = 2

    private int noteRef = 0;

    private boolean hasLoaded = false;
    private boolean isRunning = false;
    private boolean isReset = false;


    public SoundPlayer(Context context, int instrument, int soundSource) {
        changeInstrument(context, instrument, soundSource);

    }

    public void changeInstrument(Context context, int instrument, int soundSource) {
        this.instrument = instrument;

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder()
                .setMaxStreams(300)
                .setAudioAttributes(audioAttributes)
                .build();

        this.instrument = instrument;

        soundPool.load(context, soundSource, 1);

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                hasLoaded = true;
            }
        });

    }

    public void playNote(int note, int hauteur) {
        if (hasLoaded && note != 0) {
            float rate = 1.0f;

            for (int i = 0 ; i < note - 1; i++) rate = rate * ROOT;

            switch(hauteur) {
                case 1: rate = rate*islow; break;
                case 2: rate = rate*ismid; break;
                case 3: rate = rate*ishigh; break;
            }
            soundPool.play(instrument,1,1,1,0, rate);
        }
    }

    public void playTrackFromList(ArrayList<String> track, int bpm, int trackLength) {
        ArrayList<Integer> hauteurs = new ArrayList<>();
        ArrayList<String> notes = new ArrayList<>();
        isRunning = true;
        for (int i = 0; i < trackLength; i++) {
            if (!track.get(i).equals("-")) {
                hauteurs.add(Integer.parseInt(Character.toString(track.get(i).charAt(track.get(i).length() - 1))));
                notes.add(track.get(i).substring(0, track.get(i).length() - 1));
            } else {
                hauteurs.add(1);
                notes.add("-");
            }
        }

        float temp = 60F / bpm;
        long newTemp = (long) (1000L * temp);

        CountDownTimer timer = new CountDownTimer(trackLength * 1000L, newTemp) {
            @Override
            public void onTick(long l) {
                if (noteRef < trackLength) {
                    if (isRunning) {
                        playNote(noteToInt(notes.get(noteRef)), hauteurs.get(noteRef));
                        noteRef++;
                    } else {
                        this.cancel();
                    }
                }
            }

            @Override
            public void onFinish() {
                noteRef = 0;
            }
        }.start();
    }

    private int noteToInt(String note) {
        switch (note) {
            case "c": return 1;
            case "c#": return 2;
            case "d": return 3;
            case "d#": return 4;
            case "e": return 5;
            case "f": return 6;
            case "f#": return 7;
            case "g": return 8;
            case "g#": return 9;
            case "a": return 10;
            case "a#": return 11;
            case "b": return 12;
            default: return 0;
        }
    }

    public boolean getHasLoaded() {
        return hasLoaded;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public int getNoteRef() {
        return noteRef;
    }

    public void setNoteRef(int noteRef) {
        this.noteRef = noteRef;
    }
}
