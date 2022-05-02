package uqac.dim.soundsgood;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Chronometer;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import android.widget.Spinner;
import android.media.MediaPlayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements BPMDialogue.dialogueListener{

    private BPMDialogue bpmdialogue;
    private View selectedBeat = null;
    private int keyboardHeight = 2;
    public int bpm = 120;
    public int nbtracks = 3;
    public float dureedelai = 0.5F;
    public int scrollDistX = 0;
    public HorizontalScrollView horizontalscrollView;
    private SoundPool soundpool;
    boolean  loaded = false;
    private SoundPlayer soundplayer;
    private HashMap<Integer, Integer> soundsMap;
    private int piano, guitare, claves;
    private int instrument = piano;
    private ArrayList<String> SavedNoteList = new ArrayList<String>();

    //countdown timer variables
    private static final long START_TIME_IN_MILLIS = 180000;
    private TextView mTextViewCountDown;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    private TrackConstructor tracks;
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
        soundplayer = new SoundPlayer(this);
        tracks.generateTrack();

        updateCountDownText();

    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }*/


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {

            case R.id.menu_ChangerBPM:
                Log.i("DIM", "VOICI VOTRE LISTE DE MUSIQUE!");
                openDialog();

                return true;

            case R.id.menu_AjouterTrack:

                return true;

            case R.id.menu_RetirerTrack:
                
                return true;

            case R.id.menu_Sauvegarder:

                return true;

            case R.id.menu_Charger:

                return true;

            case R.id.menu_Reinitialiser:


                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }




    public void openDialog(){
        BPMDialogue bpmdialogue = new BPMDialogue();
        bpmdialogue.show(getSupportFragmentManager(), "BPM Choix");
    }

    @Override
    public void applyBPM(int nouveauBPM) {
        bpm = nouveauBPM;
        dureedelai = 60F/ nouveauBPM;
    }




    //applique une note a la trame choisie
    public void addNote(View view) throws IOException {
        if (selectedBeat == null)
        {

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                AudioAttributes audioattributes = new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build();
                soundpool = new SoundPool.Builder()
                        .setMaxStreams(1)
                        .setAudioAttributes(audioattributes)
                        .build();
            }
            else{
                soundpool = new SoundPool(1, AudioManager.STREAM_MUSIC, 100);
            }


            piano = soundpool.load(this,R.raw.guitare_do, 1);
            guitare = soundpool.load(this,R.raw.piano_do, 1);
            claves = soundpool.load(this,R.raw.claves, 1);

            /*
            switch(view.getId()){
                case R.id.instrument1: instrument = piano;break;
                case R.id.instrument2: instrument = guitare;break;
                case R.id.instrument3: instrument = claves;break;
                default: break;*/
            }



            //jouer la note

            soundpool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                public void onLoadComplete(SoundPool soundPool, int sampleId,int status) {

                    if(view == findViewById(R.id.Do)){

                       soundplayer.PlayNote("piano","c", keyboardHeight );

                    }
                    if(view == findViewById(R.id.Re)){
                        Log.i("re","isplaying");

                    }
                    if(view == findViewById(R.id.Mi)){
                        Log.i("mi","isplaying");

                        if(keyboardHeight == 3){
                            soundpool.play(instrument,1,1,0,0,2.52f);
                        }

                        if(keyboardHeight == 2){
                            soundpool.play(instrument,1,1,0,0,1.26f);
                        }

                        if(keyboardHeight == 1){
                            soundpool.play(instrument,1,1,0,0,0.63f);
                        }

                    }
                    if(view == findViewById(R.id.Fa)){
                        Log.i("fa","isplaying");

                        if(keyboardHeight == 3){
                            soundpool.play(instrument,1,1,0,0,2.66f);
                        }

                        if(keyboardHeight == 2){
                            soundpool.play(instrument,1,1,0,0,1.33f);
                        }

                        if(keyboardHeight == 1){
                            soundpool.play(instrument,1,1,0,0,0.67f);
                        }

                    }
                    if(view == findViewById(R.id.Sol)){
                        Log.i("sol","isplaying");

                        if(keyboardHeight == 3){
                            soundpool.play(instrument,1,1,0,0,3.0f);
                        }

                        if(keyboardHeight == 2){
                            soundpool.play(instrument,1,1,0,0,1.5f);
                        }

                        if(keyboardHeight == 1){
                            soundpool.play(instrument,1,1,0,0,0.75f);
                        }

                    }
                    if(view == findViewById(R.id.La)){
                        Log.i("la","isplaying");

                        if(keyboardHeight == 3){
                            soundpool.play(instrument,1,1,0,0,3.36f);
                        }

                        if(keyboardHeight == 2){
                            soundpool.play(instrument,1,1,0,0,1.68f);
                        }

                        if(keyboardHeight == 1){
                            soundpool.play(instrument,1,1,0,0,0.84f);
                        }

                    }
                    if(view == findViewById(R.id.Si)){
                        Log.i("si","isplaying");

                        if(keyboardHeight == 3){
                            soundpool.play(instrument,1,1,0,0,1.88f);
                        }

                        if(keyboardHeight == 2){
                            soundpool.play(instrument,1,1,0,0,0.94f);
                        }

                        if(keyboardHeight == 1){
                            soundpool.play(instrument,1,1,0,0,0.47f);
                        }

                    }

                    if(view == findViewById(R.id.Do_diese)){
                        Log.i("do_diese","isplaying");

                        if(keyboardHeight == 3){
                            soundpool.play(instrument,1,1,0,0,2.12f);
                        }

                        if(keyboardHeight == 2){
                            soundpool.play(instrument,1,1,0,0,1.06f);
                        }

                        if(keyboardHeight == 1){
                            soundpool.play(instrument,1,1,0,0,0.53f);
                        }

                    }
                    if(view == findViewById(R.id.Re_diese)){
                        Log.i("re_diese","isplaying");

                        if(keyboardHeight == 3){
                            soundpool.play(instrument,1,1,0,0,2.38f);
                        }

                        if(keyboardHeight == 2){
                            soundpool.play(instrument,1,1,0,0,1.19f);
                        }

                        if(keyboardHeight == 1){
                            soundpool.play(instrument,1,1,0,0,0.6f);
                        }

                    }
                    if(view == findViewById(R.id.Fa_diese)){
                        Log.i("fa_diese","isplaying");

                        if(keyboardHeight == 3){
                            soundpool.play(instrument,1,1,0,0,2.82f);
                        }

                        if(keyboardHeight == 2){
                            soundpool.play(instrument,1,1,0,0,1.41f);
                        }

                        if(keyboardHeight == 1){
                            soundpool.play(instrument,1,1,0,0,0.7f);
                        }

                    }

                    if(view == findViewById(R.id.Sol_diese)){
                        Log.i("sol_diese","isplaying");

                        if(keyboardHeight == 3){
                            soundpool.play(instrument,1,1,0,0,3.14f);
                        }

                        if(keyboardHeight == 2){
                            soundpool.play(instrument,1,1,0,0,1.57f);
                        }

                        if(keyboardHeight == 1){
                            soundpool.play(instrument,1,1,0,0,0.78f);
                        }

                    }
                    if(view == findViewById(R.id.La_diese)){
                        Log.i("la_diese","isplaying");

                        if(keyboardHeight == 3){
                            soundpool.play(instrument,1,1,0,0,3.18f);
                        }

                        if(keyboardHeight == 2){
                            soundpool.play(instrument,1,1,0,0,1.59f);
                        }

                        if(keyboardHeight == 1){
                            soundpool.play(instrument,1,1,0,0,0.8f);
                        }
                    }
                }
            });


            return;
        }



        /*
        switch(view.getId())
        {
            case R.id.Do:
               selectedBeat.setForeground(getDrawable(R.color.do_couleur));
               SavedNoteList.add("c");
               break;

            case R.id.Do_diese:
                selectedBeat.setForeground(getDrawable(R.color.do_diese_couleur));
                SavedNoteList.add("cDiese");
                break;

            case R.id.Re:
                selectedBeat.setForeground(getDrawable(R.color.re_couleur));
                SavedNoteList.add("d");
                break;

            case R.id.Re_diese:
                selectedBeat.setForeground(getDrawable(R.color.re_diese_couleur));
                break;

            case R.id.Mi:
                selectedBeat.setForeground(getDrawable(R.color.mi_couleur));
                SavedNoteList.add("e");
                break;

            case R.id.Fa:
                selectedBeat.setForeground(getDrawable(R.color.fa_couleur));
                SavedNoteList.add("f");
                break;

            case R.id.Fa_diese:
                selectedBeat.setForeground(getDrawable(R.color.fa_diese_couleur));
                break;

            case R.id.Sol:
                selectedBeat.setForeground(getDrawable(R.color.sol_couleur));
                SavedNoteList.add("g");
                break;

            case R.id.Sol_diese:
                selectedBeat.setForeground(getDrawable(R.color.sol_diese_couleur));
                break;

            case R.id.La:
                selectedBeat.setForeground(getDrawable(R.color.la_couleur));
                SavedNoteList.add("a");
                break;

            case R.id.La_diese:
                selectedBeat.setForeground(getDrawable(R.color.la_diese_couleur));
                break;

            case R.id.Si:
                selectedBeat.setForeground(getDrawable(R.color.si_couleur));
                SavedNoteList.add("b");
                break;

            case R.id.Erase:
                selectedBeat.setForeground(getDrawable(R.color.blank_couleur));
                SavedNoteList.clear(); //removes all the elements in the list
                break;
        }


        switch(keyboardHeight)
        {
            case 1:
                selectedBeat.setBackgroundResource(R.color.low);
                break;

            case 2:
                selectedBeat.setBackgroundResource(R.color.mid);
                break;

            case 3:
                selectedBeat.setBackgroundResource(R.color.high);
                break;
        }
    }*/


    public void changeHeight(View view)
    {
        switch(view.getId())
        {
            case R.id.radio_low:
                keyboardHeight = 1;
                break;

            case R.id.radio_mid:
                keyboardHeight = 2;
                break;

            case R.id.radio_high:
                keyboardHeight = 3;
                break;
        }
    }

    public void PlayButton(View view){

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

    @Override
    protected void onDestroy(){
        super.onDestroy();
        soundpool.release();
        soundpool = null;
    }



}