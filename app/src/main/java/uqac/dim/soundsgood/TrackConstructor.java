package uqac.dim.soundsgood;

import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class TrackConstructor {

    private int trackLength;
    private int tracksNumber;
    private ArrayList<ArrayList<Button>> tracks;

    private final LinearLayout reference;
    private final ArrayList<LinearLayout> realReference;

    private final GradientDrawable border;
    private final GradientDrawable baseColor;


    private Button selectedNote;

    public TrackConstructor(int trackLength, int tracksNumber, LinearLayout reference) {
        this.trackLength = trackLength;
        this.reference = reference;
        this.realReference = new ArrayList<>();
        this.tracksNumber = tracksNumber;

        baseColor = new GradientDrawable();
        baseColor.setColor(0xFFb5b8bd);
        baseColor.setStroke(15, 0xFFffffff);

        border = new GradientDrawable();
        border.setColor(0xFFb5b8bd);
        border.setStroke(15, 0xFF9e9e9e);

        tracks = new ArrayList<>();

        generateReference();
        addTracks(this.tracksNumber);
    }

    private void addTracks(int numberOfTracks) {
        for (int i = 0; i < numberOfTracks; i++) {
            tracks.add(new ArrayList<>());
            addNotes(tracks.get(i), realReference.get(i));
        }
    }

    public void addNewTracks(int numberOfTracks) {
        for (int i = 0; i < numberOfTracks; i++) {
            tracks.add(new ArrayList<>());
            realReference.add(new LinearLayout(reference.getContext()));
            realReference.get(tracksNumber + i).setOrientation(LinearLayout.HORIZONTAL);
            realReference.get(tracksNumber + i).setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
            addNotes(tracks.get(tracksNumber + i), realReference.get(tracksNumber + i));
            reference.addView(realReference.get(tracksNumber + i));
            tracksNumber++;
        }
    }

    public void removeTracks(int numberOfTracks) {
        for (int i = 1; i <= numberOfTracks; i++) {
            reference.removeView(realReference.get(tracksNumber - 1));
            realReference.remove(tracksNumber - 1);
            tracks.remove(tracksNumber - 1);
            tracksNumber--;
        }
    }

    private void addNotes(ArrayList<Button> track, LinearLayout reference) {
        for (int i = 0; i < trackLength; i++) {
            Button bouton = new Button(reference.getContext());
            bouton.setLayoutParams(new ViewGroup.LayoutParams(250, 300));
            bouton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { selectPosition(view); }
            });
            bouton.setBackground(baseColor);
            bouton.setLabelFor(i);
            track.add(bouton);
            reference.addView(bouton);
        }
    }

    public void addNewNotes(int numberOfNotes) {
        for (int i = 0; i < tracksNumber; i++)
            for (int j = 0; j < numberOfNotes; j++) {
                Button bouton = new Button(realReference.get(i).getContext());
                bouton.setLayoutParams(new ViewGroup.LayoutParams(250, 300));
                bouton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) { selectPosition(view); }
                });
                bouton.setBackground(baseColor);
                bouton.setLabelFor(trackLength + j);
                tracks.get(i).add(bouton);
                realReference.get(i).addView(bouton);
            }
        trackLength += numberOfNotes;
    }

    public void removeNotes(int numberOfNotes) {  // Delete le début au lieu de la fin
        for (int i = 0; i < tracksNumber; i++)
            for (int j = 0; j < numberOfNotes; j++) {
                realReference.get(i).removeView(tracks.get(i).get((numberOfNotes - 1) - j));
                tracks.get(i).remove((numberOfNotes - 1) - j);
            }
        trackLength -= numberOfNotes;
    }

    public void selectPosition(View v) {
        for (ArrayList actualTrack : tracks)
            for (Button bouton : (ArrayList<Button>) actualTrack) {

            }
                //bouton.setBackground(baseColor);

        v.setBackground(border);
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

    public String trackToString() {
        return "";
    }

    public int getTrackLength() {
        return trackLength;
    }

    public int getTracksNumber() {
        return tracksNumber;
    }

    private Button getSelectedNote() { return selectedNote; }
}