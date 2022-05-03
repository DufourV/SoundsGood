package uqac.dim.soundsgood;

import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class TrackConstructor {

    private int trackLength;
    private int tracksNumber;
    private final ArrayList<ArrayList<Button>> tracks;
    private final ArrayList<ArrayList<String>> actualContent;


    private final LinearLayout reference;
    private final ArrayList<LinearLayout> realReference;

    private final GradientDrawable border;
    private final GradientDrawable baseColor;

    static final int NOTE_WIDTH = 175;
    static final int NOTE_HEIGHT = 200;

    private Button selectedNote;
    private int selectedI = 0;
    private int selectedJ = 0;

    public TrackConstructor(int trackLength, int tracksNumber, LinearLayout reference) {
        this.trackLength = trackLength;
        this.reference = reference;
        this.realReference = new ArrayList<>();
        this.tracksNumber = tracksNumber;

        selectedNote = null;

        baseColor = new GradientDrawable();
        baseColor.setColor(0xFFb5b8bd);
        baseColor.setStroke(10, 0xFFffffff);

        border = new GradientDrawable();
        border.setColor(0xFFb5b8bd);
        border.setStroke(10, 0xFF9e9e9e);

        tracks = new ArrayList<>();
        actualContent = new ArrayList<>();

        generateReference();
        addTracks(this.tracksNumber);
    }

    public TrackConstructor(int trackLength, int tracksNumber, LinearLayout reference, ArrayList<ArrayList<String>> contenu) {
        this.trackLength = trackLength;
        this.reference = reference;
        this.realReference = new ArrayList<>();
        this.tracksNumber = tracksNumber;

        selectedNote = null;

        baseColor = new GradientDrawable();
        baseColor.setColor(0xFFb5b8bd);
        baseColor.setStroke(10, 0xFFffffff);

        border = new GradientDrawable();
        border.setColor(0xFFb5b8bd);
        border.setStroke(10, 0xFF9e9e9e);

        tracks = new ArrayList<>();
        actualContent = contenu;

        generateReference();
        addTracks(this.tracksNumber);

        refreshNotes();
    }

    private void addTracks(int numberOfTracks) {
        for (int i = 0; i < numberOfTracks; i++) {
            actualContent.add(new ArrayList<>());
            tracks.add(new ArrayList<>());

            addNotes(tracks.get(i), actualContent.get(i), realReference.get(i));
        }
    }

    public void addNewTracks(int numberOfTracks) {
        for (int i = 0; i < numberOfTracks; i++) {
            tracks.add(new ArrayList<>());
            actualContent.add(new ArrayList<>());

            realReference.add(new LinearLayout(reference.getContext()));
            realReference.get(tracksNumber + i).setOrientation(LinearLayout.HORIZONTAL);
            realReference.get(tracksNumber + i).setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
            addNotes(tracks.get(tracksNumber + i), actualContent.get(tracksNumber + i), realReference.get(tracksNumber + i));
            reference.addView(realReference.get(tracksNumber + i));

            tracksNumber++;
        }
    }

    public void removeTracks(int numberOfTracks) {
        for (int i = 1; i <= numberOfTracks; i++) {
            reference.removeView(realReference.get(tracksNumber - 1));
            realReference.remove(tracksNumber - 1);

            tracks.remove(tracksNumber - 1);
            actualContent.remove(tracksNumber - 1);

        }
    }

    private void addNotes(ArrayList<Button> track, ArrayList<String> actualTrack, LinearLayout reference) {
        for (int i = 0; i < trackLength; i++) {
            Button bouton = new Button(reference.getContext());
            bouton.setLayoutParams(new ViewGroup.LayoutParams(NOTE_WIDTH, NOTE_HEIGHT));
            bouton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { selectPosition(view); }
            });
            bouton.setBackground(baseColor);
            bouton.setLabelFor(i);

            track.add(bouton);
            actualTrack.add("-");

            reference.addView(bouton);
        }
    }

    public void addNewNotes(int numberOfNotes) {
        for (int i = 0; i < tracksNumber; i++) {
            for (int j = 0; j < numberOfNotes; j++) {
                Button bouton = new Button(realReference.get(i).getContext());
                bouton.setLayoutParams(new ViewGroup.LayoutParams(NOTE_WIDTH, NOTE_HEIGHT));
                bouton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectPosition(view);
                    }
                });
                bouton.setBackground(baseColor);
                bouton.setLabelFor(trackLength + j);

                tracks.get(i).add(bouton);
                actualContent.get(i).add("-");

                realReference.get(i).addView(bouton);
            }
        }
        trackLength += numberOfNotes;
    }

    public void removeNotes(int numberOfNotes) {  // Delete le début au lieu de la fin, à corriger
        for (int i = 0; i < tracksNumber; i++) {
            for (int j = 0; j < numberOfNotes; j++) {
                realReference.get(i).removeView(tracks.get(i).get((numberOfNotes - 1) - j));

                tracks.get(i).remove((numberOfNotes - 1) - j);
                actualContent.get(i).remove((numberOfNotes - 1) - j);
            }
        }
        trackLength -= numberOfNotes;
    }

    public void selectPosition(View v) {
        for (int i = 0; i < tracksNumber; i++) {
            for (int j = 0; j < trackLength; j++) {
                if (tracks.get(i).get(j).getBackground() == border) tracks.get(i).get(j).setBackground(baseColor);
            }
        }
        if (v.getBackground() == baseColor) v.setBackground(border);

        boolean found = false;
        selectedNote = (Button) v;

        for (int i = 0; i < tracksNumber; i++) {
            for (int j = 0; j < trackLength; j++) {
                if (selectedNote == tracks.get(i).get(j)) {
                    selectedI = i;
                    selectedJ = j;
                    found = true;
                    break;
                }
            }
            if (found) break;
        }
    }

    public void changeNote(String note, int couleur) {
        actualContent.get(selectedI).set(selectedJ, note);
        GradientDrawable tb = new GradientDrawable();
        tb.mutate();
        tb.setStroke(15, 0xFFffffff);
        tb.setColor(couleur);
        selectedNote.setBackground(tb);
        tb.invalidateSelf();
    }

    public void cleareNote() {
        selectedNote.setBackground(baseColor);
        actualContent.get(selectedI).set(selectedJ, "-");
    }

    public void generateTrack() {
        for (int i = 0; i < tracksNumber; i++) reference.addView(realReference.get(i));
    }

    private void generateReference() {
        for (int i = 0; i < tracksNumber; i++) {
            realReference.add(new LinearLayout(reference.getContext()));
            realReference.get(i).setOrientation(LinearLayout.HORIZONTAL);
            realReference.get(i).setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
        }
    }

    public ArrayList<ArrayList<String>> getTracks() {
        return actualContent;
    }


    public int getTrackLength() {
        return trackLength;
    }

    public int getTracksNumber() {
        return tracksNumber;
    }

    public Button getSelectedNote() { return selectedNote; }

    public int getSelectedI() {
        return selectedI;
    }

    public int getSelectedJ() {
        return selectedJ;
    }

    public ArrayList<String> getTracksArray(){
        ArrayList<String> returnarray = new ArrayList<String>();

        for (ArrayList<String> track : actualContent)
        {
            returnarray.addAll(track);
        }
        return returnarray;
    }

    private void refreshNotes()
    {
        ArrayList<String> Array = getTracksArray();
        for (int i = 0; i<tracksNumber; i++)
        {
         for (int j = 0; j<trackLength; j++)
         {
             selectedI = i;
             selectedJ = j;
             selectedNote = tracks.get(selectedI).get(selectedJ);
             int couleur;
             switch(Array.get(i*tracksNumber+j))
             {
                 case "c1":
                 case "c2":
                 case "c3":
                         couleur = (R.color.do_couleur);
                         break;

                 case "c#1":
                 case "c#2":
                 case "c#3":
                     couleur = (R.color.do_diese_couleur);
                     break;

                 case "d1" :
                 case "d2" :
                 case "d3" :
                     couleur = (R.color.re_couleur);
                     break;

                 case "d#1" :
                 case "d#2" :
                 case "d#3" :
                     couleur = (R.color.re_diese_couleur);
                     break;

                 case "e1" :
                 case "e2" :
                 case "e3" :
                     couleur = (R.color.mi_couleur);
                     break;

                 case "f1" :
                 case "f2" :
                 case "f3" :
                     couleur = (R.color.fa_couleur);
                     break;

                 case "f#1" :
                 case "f#2" :
                 case "f#3" :
                     couleur = (R.color.fa_diese_couleur);
                     break;

                 case "g1" :
                 case "g2" :
                 case "g3" :
                     couleur = (R.color.sol_couleur);
                     break;

                 case "g#1" :
                 case "g#2" :
                 case "g#3" :
                     couleur = (R.color.sol_diese_couleur);
                     break;

                 case "a1" :
                 case "a2" :
                 case "a3" :
                     couleur = (R.color.la_couleur);
                     break;

                 case "a#1" :
                 case "a#2" :
                 case "a#3" :
                     couleur = (R.color.la_diese_couleur);
                     break;

                 case "b1" :
                 case "b2" :
                 case "b3" :
                     couleur = (R.color.si_couleur);
                     break;

                 case "-" :
                     couleur = 0xFFb5b8bd;
                     break;

                 default:
                     couleur = 0xFFb5b8bd;
             }

             GradientDrawable tb = new GradientDrawable();
             tb.mutate();
             tb.setStroke(15, 0xFFffffff);
             tb.setColor(couleur);
             selectedNote.setBackground(tb);
             tb.invalidateSelf();
         }
        }
    }

    public ArrayList<String> getSpecificTrack(int i) {return actualContent.get(i);}
}