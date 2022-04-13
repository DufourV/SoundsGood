package uqac.dim.soundsgood;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
        chronometer = findViewById(R.id.chronometer);

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) { //execute a chaque seconde du chrono

                if((SystemClock.elapsedRealtime() - chronometer.getBase()) >= (dureedelai * 1000f)  ) { //scroll a chaque tick selon le bpm une colonne a la fois

                    horizontalscrollView.scrollTo(scrollDistX, 0);
                    scrollDistX += 120;

                }

                if((SystemClock.elapsedRealtime() - chronometer.getBase()) >= 3000  ) {//2 sec

                    //mets le background de la premiere colone en blanc
                    LinearLayout ColbackgroundColor = ((LinearLayout)findViewById(R.id.Colonne2));
                    ColbackgroundColor.setBackgroundColor(getColor(R.color.white));

                    ColbackgroundColor = ((LinearLayout)findViewById(R.id.Colonne3));
                    ColbackgroundColor.setBackgroundColor(getColor(R.color.black));
                }

                 */
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
    public void addNote(View view)
    {
        if (selectedBeat == null)
        {
            //jouer la note
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

        scrollDistX = 0;
        horizontalscrollView.scrollTo(scrollDistX, 0);

    }
}