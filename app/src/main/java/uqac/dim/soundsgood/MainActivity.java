package uqac.dim.soundsgood;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements BPMDialogue.dialogueListener{

    private BPMDialogue bpmdialogue;
    private View selectedBeat = null;
    private int keyboardHeight = 2;
    public int bpm = 60;
    public int nbtracks = 3;
    public float dureedelai = 0.5F;
    public int scrollDistX = 0;
    public HorizontalScrollView horizontalscrollView;
    public ArrayList<Integer> instrumentArray;

    //countdown timer variables
    private static final long START_TIME_IN_MILLIS = 180000;
    private TextView mTextViewCountDown;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    private TrackConstructor tracks;
    private ArrayList<SoundPlayer> soundPlayers;

    private SGSaver saver;

    ActivityResultLauncher<Intent> activityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK){
                        Intent intent = result.getData();
                        if(intent != null) {
                            //extract data
                            instrumentArray.clear();

                            instrumentArray = intent.getIntegerArrayListExtra("resultArray");
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        horizontalscrollView = ((HorizontalScrollView)findViewById(R.id.horizontal)); //variable pour le scroll horizontal
        mTextViewCountDown = findViewById(R.id.text_view_countdown);

        tracks = new TrackConstructor(15, 3, (LinearLayout) findViewById(R.id.linearTracks));
        soundPlayers = new ArrayList<>();
        for (int i = 0; i < tracks.getTracksNumber(); i++) soundPlayers.add(new SoundPlayer(getApplicationContext(), 1, R.raw.piano_do));
        tracks.generateTrack();

        updateCountDownText();

        instrumentArray = new ArrayList<Integer>();
        for(int i = 0; i <= 3; i++){
            instrumentArray.add(i, 0);
        }
        instrumentArray.remove(3);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public void openActivityParametres() {
        Intent intent = new Intent(this, Parametres.class);
        intent.putExtra("BPM_Actuel", bpm);
        intent.putExtra("NB_Tracks", nbtracks);
        intent.putExtra("Array", instrumentArray);
        activityLauncher.launch(intent);
    }

    public void openActivityListeMusique() {
        Intent intent = new Intent(this, ListeMusique.class);
        activityLauncher.launch(intent);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_ChangerBPM:
                openDialog(); return true;
            case R.id.menu_Parametres:
                openActivityParametres();
                return true;
            case R.id.menu_Sauvegarder:
                return true;
            case R.id.menu_Charger: return true;
            case R.id.menu_Reinitialiser: return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    public void openDialog(){
        BPMDialogue bpmdialogue = new BPMDialogue();
        bpmdialogue.show(getSupportFragmentManager(), "BPM Choix");
    }

    @Override
    public void applyBPM(int nouveauBPM) {
        bpm = nouveauBPM;
        dureedelai = 60F/nouveauBPM;
    }

    @SuppressLint("NonConstantResourceId")
    public void addNote(View view) throws IOException {
        switch (view.getId()) { // Ajouter le playNote une fois en place
            case R.id.Do: tracks.changeNote("c" + keyboardHeight, getColor(R.color.do_couleur));
                soundPlayers.get(tracks.getSelectedI()).playNote(1, keyboardHeight); break;
            case R.id.Do_diese: tracks.changeNote("c#" + keyboardHeight, getColor(R.color.do_diese_couleur));
                soundPlayers.get(tracks.getSelectedI()).playNote(2, keyboardHeight); break;
            case R.id.Re: tracks.changeNote("d" + keyboardHeight, getColor(R.color.re_couleur));
                soundPlayers.get(tracks.getSelectedI()).playNote(3, keyboardHeight); break;
            case R.id.Re_diese: tracks.changeNote("d#" + keyboardHeight, getColor(R.color.re_diese_couleur));
                soundPlayers.get(tracks.getSelectedI()).playNote(4, keyboardHeight); break;
            case R.id.Mi: tracks.changeNote("e" + keyboardHeight, getColor(R.color.mi_couleur));
                soundPlayers.get(tracks.getSelectedI()).playNote(5, keyboardHeight); break;
            case R.id.Fa: tracks.changeNote("f" + keyboardHeight, getColor(R.color.fa_couleur));
                soundPlayers.get(tracks.getSelectedI()).playNote(6, keyboardHeight); break;
            case R.id.Fa_diese: tracks.changeNote("f#" + keyboardHeight, getColor(R.color.fa_diese_couleur));
                soundPlayers.get(tracks.getSelectedI()).playNote(7, keyboardHeight); break;
            case R.id.Sol: tracks.changeNote("g" + keyboardHeight, getColor(R.color.sol_couleur));
                soundPlayers.get(tracks.getSelectedI()).playNote(8, keyboardHeight); break;
            case R.id.Sol_diese: tracks.changeNote("g#" + keyboardHeight, getColor(R.color.sol_diese_couleur));
                soundPlayers.get(tracks.getSelectedI()).playNote(9, keyboardHeight); break;
            case R.id.La: tracks.changeNote("a" + keyboardHeight, getColor(R.color.la_couleur));
                soundPlayers.get(tracks.getSelectedI()).playNote(10, keyboardHeight); break;
            case R.id.La_diese: tracks.changeNote("a#" + keyboardHeight, getColor(R.color.la_diese_couleur));
                soundPlayers.get(tracks.getSelectedI()).playNote(11, keyboardHeight); break;
            case R.id.Si: tracks.changeNote("b" + keyboardHeight, getColor(R.color.si_couleur));
                soundPlayers.get(tracks.getSelectedI()).playNote(12, keyboardHeight); break;
            case R.id.Erase: tracks.cleareNote(); break;
        }
    }

    @SuppressLint("NonConstantResourceId")
    public void changeHeight(View view) {
        switch(view.getId()) {
            case R.id.radio_low: keyboardHeight = 1; break;
            case R.id.radio_mid: keyboardHeight = 2; break;
            case R.id.radio_high: keyboardHeight = 3; break;
        }
    }

    public void PlayButton(View view){
        for (int i = 0 ; i < tracks.getTracksNumber(); i++) {
            soundPlayers.get(i).playTrackFromList(tracks.getTracks().get(i), bpm, tracks.getTrackLength());
        }

        if(!mTimerRunning) {

           mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, (long) (dureedelai * 1000)) {
               @Override
               public void onTick(long millisUntilFinished) {

                   mTimeLeftInMillis = millisUntilFinished;
                   updateCountDownText();
                   Log.i("DIM", String.valueOf((long) (dureedelai * 1000)));

                   horizontalscrollView.scrollTo(scrollDistX, 0);
                   scrollDistX += 118;
               }

               @Override
               public void onFinish() {
                   mTimerRunning = false;
               }
           }.start();

            mTimerRunning = true;
            findViewById(R.id.Reset).setVisibility(View.GONE);
        }

    }

    private void updateCountDownText(){
        int minutes = (int) (mTimeLeftInMillis /1000) / 60;
        int seconds = (int) (mTimeLeftInMillis /1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);
        mTextViewCountDown.setText(timeLeftFormatted);

        Log.i("DIM", timeLeftFormatted);
    }

    public void PauseButton(View view){
        mCountDownTimer.cancel();
        mTimerRunning = false;
        findViewById(R.id.Reset).setVisibility(View.VISIBLE);
    }

    public void ResetTimerButton(View view){
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        ResetDefilement();
    }

    public void ResetDefilement(){ //reset le background noir
        scrollDistX = 0;
        horizontalscrollView.scrollTo(scrollDistX, 0);

    }

    public void AddTrack(View view){
        tracks.addNewTracks(1);
        soundPlayers.add(new SoundPlayer(getApplicationContext(), 1, R.raw.piano_do));

        instrumentArray.add(nbtracks, 0);
        nbtracks = nbtracks + 1;
    }

    public void RemoveTrack(View view){
        if (tracks.getTracksNumber() > 0) {
            tracks.removeTracks(1);
            soundPlayers.remove(soundPlayers.size() - 1);

            instrumentArray.remove(nbtracks-1);
            nbtracks = nbtracks - 1;
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }


    public void sauvegarde(MenuItem item) {

    }

    public void chargement(MenuItem item) {
        openActivityListeMusique(); //Methode qui ouvre la liste de musique
    }
}