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
import android.os.SystemClock;
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
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements BPMDialogue.dialogueListener{

    private BPMDialogue bpmdialogue;
    //Chrono variables
    private Chronometer chronometer;
    private boolean running;
    private long pauseOffset;
    private View selectedBeat = null;
    private int keyboardHeight = 2;
    public int bpm = 120;
    public int nbtracks = 3;
    public float dureedelai = 0.5F;
    public int scrollDistX = 0;
    public HorizontalScrollView horizontalscrollView;
    private SoundPool soundpool;
    boolean  loaded = false;
    private HashMap<Integer, Integer> soundsMap;
    private int piano, guitare, claves;
    private int instrument = piano;


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

    public MainActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        horizontalscrollView = ((HorizontalScrollView)findViewById(R.id.horizontal)); //variable pour le scroll horizontal
        chronometer = findViewById(R.id.chronometer);

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) { //execute a chaque seconde du chrono

                if((SystemClock.elapsedRealtime() - chronometer.getBase()) >= (dureedelai * 1000f)  ) { //scroll a chaque tick selon le bpm une colonne a la fois

                    horizontalscrollView.scrollTo(scrollDistX, 0);
                    scrollDistX += 118;

                }


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public void openActivityListeMusique() {
        Intent intent = new Intent(this, ListeMusique.class);
        activityLauncher.launch(intent);
    }

    public void openActivityParametres() {
        Intent intent = new Intent(this, Parametres.class);
        activityLauncher.launch(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {

            case R.id.menu_ChangerBPM:
                Log.i("DIM", "VOICI VOTRE LISTE DE MUSIQUE!");
                openDialog();

                return true;

            case R.id.menu_AjouterTrack:
                addtrack();
                return true;

            case R.id.menu_RetirerTrack:
                removetrack();
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

    public void addtrack()
    {
        switch(nbtracks)
        {
            case 1:
                findViewById(R.id.track2).setVisibility(View.VISIBLE);
                findViewById(R.id.instrument2).setVisibility(View.VISIBLE);
                break;

            case 2:
                findViewById(R.id.track3).setVisibility(View.VISIBLE);
                findViewById(R.id.instrument3).setVisibility(View.VISIBLE);
                break;

            case 3:
                findViewById(R.id.track4).setVisibility(View.VISIBLE);
                findViewById(R.id.instrument4).setVisibility(View.VISIBLE);
                break;

            case 4:
                findViewById(R.id.track5).setVisibility(View.VISIBLE);
                findViewById(R.id.instrument5).setVisibility(View.VISIBLE);
                break;

            case 5:
                findViewById(R.id.track6).setVisibility(View.VISIBLE);
                findViewById(R.id.instrument6).setVisibility(View.VISIBLE);
                break;

            case 6:
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.track_toast, (ViewGroup) findViewById(R.id.track_toast_linearlayout));
                TextView tv = (TextView) layout.findViewById(R.id.toast_text);
                tv.setText(R.string.maxtrack);
                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 100);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
                return;
        }
        nbtracks++;
    }

    public void removetrack()
    {
        switch(nbtracks)
        {
            case 1:
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.track_toast, (ViewGroup) findViewById(R.id.track_toast_linearlayout));
                TextView tv = (TextView) layout.findViewById(R.id.toast_text);
                tv.setText(R.string.mintrack);
                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 100);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
                return;

            case 2:
                findViewById(R.id.track2).setVisibility(View.GONE);
                findViewById(R.id.instrument2).setVisibility(View.GONE);
                break;

            case 3:
                findViewById(R.id.track3).setVisibility(View.GONE);
                findViewById(R.id.instrument3).setVisibility(View.GONE);
                break;

            case 4:
                findViewById(R.id.track4).setVisibility(View.GONE);
                findViewById(R.id.instrument4).setVisibility(View.GONE);
                break;

            case 5:
                findViewById(R.id.track5).setVisibility(View.GONE);
                findViewById(R.id.instrument5).setVisibility(View.GONE);
                break;

            case 6:
                findViewById(R.id.track6).setVisibility(View.GONE);
                findViewById(R.id.instrument6).setVisibility(View.GONE);
                break;
        }
        nbtracks--;
    }

    public void openDialog(){
        BPMDialogue bpmdialogue = new BPMDialogue();
        bpmdialogue.show(getSupportFragmentManager(), "BPM Choix");
    }

    @Override
    public void applyBPM(int nouveauBPM) {
        bpm = nouveauBPM;
        dureedelai = 60f/ (float)nouveauBPM;
    }

    public void SelectBeat (View view)
    {
        selectedBeat = view;
    }


    //applique une note a la trame choisie
    public void addNote(View view) throws IOException {
        if (selectedBeat == null)
        {
            /*final MediaPlayer re_note_piano = MediaPlayer.create(this, R.raw.re_note_piano);
            final MediaPlayer mi_note_piano = MediaPlayer.create(this, R.raw.mi_note_piano);
            final MediaPlayer fa_note_piano = MediaPlayer.create(this, R.raw.fa_note_piano);
            final MediaPlayer sol_note_piano = MediaPlayer.create(this, R.raw.sol_note_piano);
            final MediaPlayer la_note_piano = MediaPlayer.create(this, R.raw.la_note_piano);
            final MediaPlayer si_note_piano = MediaPlayer.create(this, R.raw.si_note_piano);
            final MediaPlayer do_note_piano = MediaPlayer.create(this, R.raw.do_note_piano);
            final MediaPlayer re_note_guitare = MediaPlayer.create(this, R.raw.re_note_guitare);
            final MediaPlayer claves = MediaPlayer.create(this, R.raw.claves);*/

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


            piano = soundpool.load(this,R.raw.do_note_piano, 1);
            guitare = soundpool.load(this,R.raw.re_note_guitare, 1);
            claves = soundpool.load(this,R.raw.claves, 1);

            switch(view.getId()){
                case R.id.instrument1: instrument = piano;break;
                case R.id.instrument2: instrument = guitare;break;
                case R.id.instrument3: instrument = claves;break;
                default: break;
            }



            //jouer la note

            soundpool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                public void onLoadComplete(SoundPool soundPool, int sampleId,int status) {

                    if(view == findViewById(R.id.Do)){

                        Log.i("do","isplaying");


                        if(keyboardHeight == 3){
                            soundpool.play(instrument,1,1,0,0,2.0f);

                        }

                        if(keyboardHeight == 2){
                            soundpool.play(instrument,1,1,0,0,1.0f);
                        }

                        if(keyboardHeight == 1){
                            soundpool.play(instrument,1,1,0,0, 0.5f);
                        }

                    }
                    if(view == findViewById(R.id.Re)){
                        Log.i("re","isplaying");

                        if(keyboardHeight == 3){
                            soundpool.play(instrument,1,1,0,0,2.24f);
                        }

                        if(keyboardHeight == 2){
                            soundpool.play(instrument,1,1,0,0,1.12f);
                        }

                        if(keyboardHeight == 1){
                            soundpool.play(instrument,1,1,0,0,0.56f);
                        }


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




        switch(view.getId())
        {
            case R.id.Do:
               selectedBeat.setForeground(getDrawable(R.color.do_couleur));
               break;

            case R.id.Do_diese:
                selectedBeat.setForeground(getDrawable(R.color.do_diese_couleur));
                break;

            case R.id.Re:
                selectedBeat.setForeground(getDrawable(R.color.re_couleur));
                break;

            case R.id.Re_diese:
                selectedBeat.setForeground(getDrawable(R.color.re_diese_couleur));
                break;

            case R.id.Mi:
                selectedBeat.setForeground(getDrawable(R.color.mi_couleur));
                break;

            case R.id.Fa:
                selectedBeat.setForeground(getDrawable(R.color.fa_couleur));
                break;

            case R.id.Fa_diese:
                selectedBeat.setForeground(getDrawable(R.color.fa_diese_couleur));
                break;

            case R.id.Sol:
                selectedBeat.setForeground(getDrawable(R.color.sol_couleur));
                break;

            case R.id.Sol_diese:
                selectedBeat.setForeground(getDrawable(R.color.sol_diese_couleur));
                break;

            case R.id.La:
                selectedBeat.setForeground(getDrawable(R.color.la_couleur));
                break;

            case R.id.La_diese:
                selectedBeat.setForeground(getDrawable(R.color.la_diese_couleur));
                break;

            case R.id.Si:
                selectedBeat.setForeground(getDrawable(R.color.si_couleur));
                break;

            case R.id.Erase:
                selectedBeat.setForeground(getDrawable(R.color.blank_couleur));
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
    }


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

        if(!running) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;

            findViewById(R.id.scrollbuffer).setVisibility(View.VISIBLE);

        }

    }

    public void PauseButton(View view){

        if(running){
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;

            findViewById(R.id.scrollbuffer).setVisibility(View.GONE);
        }
    }

    public void RecordButton(View view){

    }
    public void ResetTimerButton(View view){
        chronometer.setBase(SystemClock.elapsedRealtime()); //reset le temps du chrono a 0
        pauseOffset = 0;

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