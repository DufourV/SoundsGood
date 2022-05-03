package uqac.dim.soundsgood;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BPMDialogue.dialogueListener, AddNotesDialogue.dialogueListener2 {


    private int keyboardHeight = 2;
    public int bpm = 60;
    public HorizontalScrollView horizontalscrollView;
    public ArrayList<Integer> instrumentArray;
    public String TrackName = "Nom de l'enregistrement";

    private boolean isPlayRunning = false;
    private TextView mTextViewCountDown;

    private TrackConstructor tracks;
    private ArrayList<SoundPlayer> soundPlayers;

    ActivityResultLauncher<Intent> activityLauncher = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == RESULT_OK){
                    Intent intent = result.getData();
                    if(intent != null) {
                        instrumentArray.clear();
                        instrumentArray = intent.getIntegerArrayListExtra("resultArray");

                        for (int i = 0; i < tracks.getTracksNumber(); i++) {
                            int tempSource = 0;
                            switch (instrumentArray.get(i)) {
                                case 0: default: tempSource = R.raw.piano_do; break;
                                case 1: tempSource = R.raw.guitare_do; break;
                                case 2: tempSource = R.raw.claves; break;
                            }
                            soundPlayers.get(i).changeInstrument(getApplicationContext(), 1, tempSource);
                        }

                        TrackName = intent.getStringExtra("resultName");

                    }
                }
                if(result.getResultCode() == RESULT_FIRST_USER) {
                    Intent intent = result.getData();
                    if (intent != null) {
                        instrumentArray.clear();
                        instrumentArray = intent.getIntegerArrayListExtra("resultPathChargement");
                    }
                }

            }
        }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        horizontalscrollView = ((HorizontalScrollView)findViewById(R.id.horizontal));
        mTextViewCountDown = findViewById(R.id.text_view_countdown);

        tracks = new TrackConstructor(15, 3, (LinearLayout) findViewById(R.id.linearTracks));
        soundPlayers = new ArrayList<>();
        for (int i = 0; i < tracks.getTracksNumber(); i++)
            soundPlayers.add(new SoundPlayer(getApplicationContext(), 1, R.raw.piano_do, bpm));
        tracks.generateTrack();

        soundPlayers.get(0).setInitialTiming(tracks.getTrackLength(), mTextViewCountDown);

        instrumentArray = new ArrayList<Integer>();
        for(int i = 0; i < tracks.getTracksNumber(); i++){
            instrumentArray.add(i, 0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public void openActivityParametres() {
        Intent intent = new Intent(this, Parametres.class);
        intent.putExtra("BPM_Actuel", bpm);
        intent.putExtra("Track_name", TrackName);
        intent.putExtra("NB_Tracks", tracks.getTracksNumber());
        intent.putExtra("Array", instrumentArray);
        activityLauncher.launch(intent);
    }

    public void openActivityListeEnregistrement(){
        Intent intent = new Intent(this, ListeEnregistrement.class);
        intent.putExtra("ListeNotes", instrumentArray); //va falloir mettre le bon intent ici
        activityLauncher.launch(intent);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_ChangerBPM:
                openDialog(); return true;
            case R.id.menu_AjouterNotes: return true;
            case R.id.menu_Parametres:
                openActivityParametres(); return true;
            case R.id.menu_Sauvegarder:
                sauvegarde(item); return true;
            case R.id.menu_Charger:
                chargement(item); return true;
            case R.id.menu_Reinitialiser:
                reinitialiser(); return true;
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
        for (SoundPlayer soundPlayer : soundPlayers) soundPlayer.changeBpm(bpm);
        if (tracks.getTracksNumber() > 0) soundPlayers.get(0).setInitialTiming(tracks.getTrackLength(), mTextViewCountDown);
    }

    @SuppressLint("NonConstantResourceId")
    public void addNote(View view) throws IOException {
        if (tracks.getSelectedNote() == null || isPlayRunning) return;

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
            case R.id.Erase: tracks.clearNote(); break;
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

    public void playButton(View view){
        if (!isPlayRunning) {
            isPlayRunning = true;
            soundPlayers.get(0).playTrackFromListWithView(tracks.getTracks().get(0), bpm, tracks.getTrackLength(), mTextViewCountDown, tracks, horizontalscrollView);
            for (int i = 1 ; i < tracks.getTracksNumber(); i++) {
                soundPlayers.get(i).playTrackFromList(tracks.getTracks().get(i), tracks.getTrackLength());
            }
            findViewById(R.id.Reset).setVisibility(View.GONE);
        }
    }

    public void pauseButton(View view){
        if (isPlayRunning) {
            isPlayRunning = false;
            findViewById(R.id.Reset).setVisibility(View.VISIBLE);
            for (SoundPlayer soundPlayer : soundPlayers) soundPlayer.setRunning(false);
        }
    }

    public void resetTimerButton(View view){
        if (!isPlayRunning) {
            if (soundPlayers.size() > 0) {
                if (soundPlayers.get(0).getNoteRef() > 0) {
                    horizontalscrollView.scrollTo(0, 0);
                    for (int i = 0; i < tracks.getTracksNumber(); i++) tracks.getButtons().get(i).get(soundPlayers.get(0).getNoteRef() - 1).setAlpha(1f);
                    for (SoundPlayer soundPlayer : soundPlayers) soundPlayer.setNoteRef(0);
                    if (tracks.getTracksNumber() > 0) soundPlayers.get(0).setInitialTiming(tracks.getTrackLength(), mTextViewCountDown);
                }
            }
        }
    }

    public void addTrack(View view){
        if (!isPlayRunning){
            tracks.addNewTracks(1);
            soundPlayers.add(new SoundPlayer(getApplicationContext(), 1, R.raw.piano_do, bpm));

            instrumentArray.add(tracks.getTracksNumber() - 1, 0);
        }
    }

    public void removeTrack(View view){
        if (tracks.getTracksNumber() > 0 && !isPlayRunning) {
            tracks.removeTracks(1);
            soundPlayers.remove(soundPlayers.size() - 1);
            instrumentArray.remove(instrumentArray.size() - 1);
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    public void sauvegarde(MenuItem item) {
        SGSaver saver= new SGSaver(tracks.getTracksNumber(), bpm, tracks.getTracksArray(), tracks.getTrackLength(), "test");

        saver.save();
    }

    public void chargement(MenuItem item) {
        tracks = new TrackConstructor(15, 3, (LinearLayout) findViewById(R.id.linearTracks));

        openActivityListeEnregistrement();
    }

    public void addNewNotes(MenuItem item) {
        AddNotesDialogue addNotesDialogue = new AddNotesDialogue();
        addNotesDialogue.show(getSupportFragmentManager(), "Notes Choix");
    }

    public void reinitialiser() {
        tracks.delete();
        tracks = new TrackConstructor(15, 3, (LinearLayout) findViewById(R.id.linearTracks));
        soundPlayers = new ArrayList<>();
        for (int i = 0; i < tracks.getTracksNumber(); i++)
            soundPlayers.add(new SoundPlayer(getApplicationContext(), 1, R.raw.piano_do, bpm));
        tracks.generateTrack();

        soundPlayers.get(0).setInitialTiming(tracks.getTrackLength(), mTextViewCountDown);

        instrumentArray = new ArrayList<Integer>();
        for(int i = 0; i < tracks.getTracksNumber(); i++){
            instrumentArray.add(i, 0);
        }
    }

    @Override
    public void applyNewNotes(int nouveauNbNotes){
        if (nouveauNbNotes > tracks.getTrackLength()) tracks.addNewNotes(nouveauNbNotes - tracks.getTracksNumber());
        else if (nouveauNbNotes < tracks.getTrackLength()) tracks.removeNotes(tracks.getTrackLength() - nouveauNbNotes);
    }
}