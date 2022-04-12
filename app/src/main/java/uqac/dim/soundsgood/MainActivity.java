package uqac.dim.soundsgood;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
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
import android.widget.Chronometer;
import android.widget.LinearLayout;
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

public class MainActivity extends AppCompatActivity {

    //Chrono variables
    private Chronometer chronometer;
    private boolean running;
    private long pauseOffset;

    private View selectedBeat = null;
    private int keyboardHeight = 2;
    //MediaPlayer mp = MediaPlayer.create(this, R.raw.);

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

        chronometer = findViewById(R.id.chronometer);

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) { //execute a chaque seconde du chrono

                if((SystemClock.elapsedRealtime() - chronometer.getBase()) >= 1000  ) { //1sec

                    //mets le background de la premiere colone en noir
                    LinearLayout ColbackgroundColor = ((LinearLayout)findViewById(R.id.PremiereColonne));
                    ColbackgroundColor.setBackgroundColor(getColor(R.color.black));
                }

                if((SystemClock.elapsedRealtime() - chronometer.getBase()) >= 2000  ) {//2 sec

                    //mets le background de la premiere colone en blanc
                    LinearLayout ColbackgroundColor = ((LinearLayout)findViewById(R.id.PremiereColonne));
                    ColbackgroundColor.setBackgroundColor(getColor(R.color.white));

                    ColbackgroundColor = ((LinearLayout)findViewById(R.id.Colonne2));
                    ColbackgroundColor.setBackgroundColor(getColor(R.color.black));
                }

                if((SystemClock.elapsedRealtime() - chronometer.getBase()) >= 3000  ) {//2 sec

                    //mets le background de la premiere colone en blanc
                    LinearLayout ColbackgroundColor = ((LinearLayout)findViewById(R.id.Colonne2));
                    ColbackgroundColor.setBackgroundColor(getColor(R.color.white));

                    ColbackgroundColor = ((LinearLayout)findViewById(R.id.Colonne3));
                    ColbackgroundColor.setBackgroundColor(getColor(R.color.black));
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
                openActivityListeMusique();

                return true;

            case R.id.menu_AjouterTrack:
                Log.i("DIM", "VOICI LES PARAMETRES!");
                openActivityParametres();

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

    public void SelectBeat (View view)
    {

        if(selectedBeat != null)
        {
            deselectColor(selectedBeat);
        }

        selectedBeat = view;

        selectColor(selectedBeat);
    }


    //remet la couleur déhighlightée quand tu cliques ailleurs
    @SuppressLint("UseCompatLoadingForDrawables")
    public void deselectColor(View view)
    {
        /*
        Drawable selectedForeground = view.getForeground();


        if (selectedForeground.equals(getDrawable(R.color.blank_selected)))
            selectedBeat.setForeground(getDrawable(R.color.blank_unselected));

        else if (selectedForeground.equals(getDrawable(R.color.do_selected)))
            selectedBeat.setForeground(getDrawable(R.color.do_unselected));

        else if (selectedForeground.equals(getDrawable(R.color.do_diese_selected)))
            selectedBeat.setForeground(getDrawable(R.color.do_diese_unselected));

        else if (selectedForeground.equals(getDrawable(R.color.re_selected)))
            selectedBeat.setForeground(getDrawable(R.color.re_unselected));

        else if (selectedForeground.equals(getDrawable(R.color.re_diese_selected)))
            selectedBeat.setForeground(getDrawable(R.color.re_diese_unselected));

        else if (selectedForeground.equals(getDrawable(R.color.mi_selected)))
            selectedBeat.setForeground(getDrawable(R.color.mi_unselected));

        else if (selectedForeground.equals(getDrawable(R.color.fa_selected)))
            selectedBeat.setForeground(getDrawable(R.color.fa_unselected));

        else if (selectedForeground.equals(getDrawable(R.color.fa_diese_selected)))
            selectedBeat.setForeground(getDrawable(R.color.fa_diese_unselected));

        else if (selectedForeground.equals(getDrawable(R.color.sol_selected)))
            selectedBeat.setForeground(getDrawable(R.color.si_unselected));

        else if (selectedForeground.equals(getDrawable(R.color.sol_diese_selected)))
            selectedBeat.setForeground(getDrawable(R.color.si_unselected));

        else if (selectedForeground.equals(getDrawable(R.color.la_selected)))
            selectedBeat.setForeground(getDrawable(R.color.la_unselected));

        else if (selectedForeground.equals(getDrawable(R.color.la_diese_selected)))
            selectedBeat.setForeground(getDrawable(R.color.la_diese_unselected));

        else if (selectedForeground.equals(getDrawable(R.color.si_selected)))
            selectedBeat.setForeground(getDrawable(R.color.si_unselected));

        else if (selectedForeground.equals(getDrawable(R.color.custom_selected)))
            selectedBeat.setForeground(getDrawable(R.color.custom_unselected));

         */
    }


    //switche la couleur quand tu cliques sur un endroit de la trame
    @SuppressLint("UseCompatLoadingForDrawables")
    public void selectColor(View view)
    {
        /*
        Drawable selectedForeground = view.getForeground();
        if (selectedForeground.equals(getDrawable(R.color.blank_unselected)))
            selectedBeat.setForeground(getDrawable(R.color.blank_selected));

        else if (selectedForeground.equals(getDrawable(R.color.do_unselected)))
            selectedBeat.setForeground(getDrawable(R.color.do_selected));

        else if (selectedForeground.equals(getDrawable(R.color.do_diese_unselected)))
            selectedBeat.setForeground(getDrawable(R.color.do_diese_selected));

        else if (selectedForeground.equals(getDrawable(R.color.re_unselected)))
            selectedBeat.setForeground(getDrawable(R.color.re_selected));

        else if (selectedForeground.equals(getDrawable(R.color.re_diese_unselected)))
            selectedBeat.setForeground(getDrawable(R.color.re_diese_selected));

        else if (selectedForeground.equals(getDrawable(R.color.mi_unselected)))
            selectedBeat.setForeground(getDrawable(R.color.mi_selected));

        else if (selectedForeground.equals(getDrawable(R.color.fa_unselected)))
            selectedBeat.setForeground(getDrawable(R.color.fa_selected));

        else if (selectedForeground.equals(getDrawable(R.color.fa_diese_unselected)))
            selectedBeat.setForeground(getDrawable(R.color.fa_diese_selected));

        else if (selectedForeground.equals(getDrawable(R.color.sol_unselected)))
            selectedBeat.setForeground(getDrawable(R.color.sol_selected));

        else if (selectedForeground.equals(getDrawable(R.color.sol_diese_unselected)))
            selectedBeat.setForeground(getDrawable(R.color.sol_diese_selected));

        else if (selectedForeground.equals(getDrawable(R.color.la_unselected)))
            selectedBeat.setForeground(getDrawable(R.color.la_selected));

        else if (selectedForeground.equals(getDrawable(R.color.la_diese_unselected)))
            selectedBeat.setForeground(getDrawable(R.color.la_diese_selected));

        else if (selectedForeground.equals(getDrawable(R.color.si_unselected)))
            selectedBeat.setForeground(getDrawable(R.color.si_selected));

        else if (selectedForeground.equals(getDrawable(R.color.custom_unselected)))
            selectedBeat.setForeground(getDrawable(R.color.custom_selected));
         */
    }

    //applique une note a la trame choisie
    public void addNote(View view) throws IOException {
        if (selectedBeat == null)
        {
            //jouer la note
            if(view == findViewById(R.id.Do)){

                Log.i("do","isplaying");

              //mp.start();

              /* MediaPlayer do_note = new MediaPlayer();
              try{
                  do_note.setDataSource("https://audio.oxforddictionaries.com/en/mp3/coward_gb_1.mp3");
                  do_note.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                      @Override
                      public void onPrepared(MediaPlayer mediaPlayer) {
                          do_note.start();
                      }
                  });
                  do_note.prepareAsync();
              }

              catch(IOException e){
                   e.printStackTrace();
                } */
            }
            if(view == findViewById(R.id.Re)){

            }
            if(view == findViewById(R.id.Mi)){

            }
            if(view == findViewById(R.id.Fa)){

            }
            if(view == findViewById(R.id.Sol)){

            }
            if(view == findViewById(R.id.La)){

            }
            if(view == findViewById(R.id.Si)){

            }
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

        }

    }

    public void PauseButton(View view){

        if(running){
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
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
        LinearLayout ColbackgroundColor = ((LinearLayout)findViewById(R.id.PremiereColonne));
        ColbackgroundColor.setBackgroundColor(getColor(R.color.white));

        ColbackgroundColor = ((LinearLayout)findViewById(R.id.Colonne2));
        ColbackgroundColor.setBackgroundColor(getColor(R.color.white));

        ColbackgroundColor = ((LinearLayout)findViewById(R.id.Colonne3));
        ColbackgroundColor.setBackgroundColor(getColor(R.color.white));

        
    }


}