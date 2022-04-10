package uqac.dim.soundsgood;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
    private View selectedBeat = null;
    private int keyboardHeight = 2;
    public int bpm = 120;
    public float dureedelai = 0.5F;

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

    }

    public void PauseButton(View view){

    }

    public void RecordButton(View view){

    }


}