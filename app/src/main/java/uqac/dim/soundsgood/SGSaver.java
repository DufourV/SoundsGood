package uqac.dim.soundsgood;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.storage.StorageManager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.io.*;
import java.util.ArrayList;
import java.util.Locale;

public class SGSaver {

    private int numberOfTracks = 0;
    private int bpm = 0;
    private ArrayList<ArrayList<String>> trackContent;
    private int trackLength = 0;
    private String trackName = "";

    public SGSaver() {
    }

    public SGSaver(int numberOfTracks, int bpm, ArrayList<ArrayList<String>> trackContent, int trackLength, String trackName) {
        this.numberOfTracks = numberOfTracks;
        this.bpm = bpm;
        this.trackContent = trackContent;
        this.trackLength = trackLength;
        this.trackName = trackName;
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public String save(Context context, StorageManager storageManager) {
        String fileLocation = "";

        String fileName = trackName.toLowerCase(Locale.ROOT).replaceAll(" ", "_").replaceAll("'", "") + System.currentTimeMillis() + ".sg";
        File directory = new File(storageManager.getPrimaryStorageVolume().getDirectory().getAbsolutePath() + "/Documents") ;

        try {
            File sFile = new File(directory, fileName);
            sFile.setWritable(true);
            if (sFile.createNewFile()) {
                fileLocation = sFile.getAbsolutePath();
                try {
                    FileWriter sWriter = new FileWriter(sFile);
                    sWriter.write(this.toString());
                    sWriter.close();
                } catch (IOException f) {
                    f.printStackTrace();
                    return "";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return fileLocation;
    }

    public SGSaver load(String contentLocation) {
        File lFile = new File(contentLocation);
        ArrayList<String> lContent = new ArrayList<>();
        if (lFile.exists()) {
            try {
                BufferedReader lReader = new BufferedReader(new FileReader(lFile));
                String temp;
                while ((temp = lReader.readLine()) != null) lContent.add(temp);
                lReader.close();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } else return null;

        if (!lContent.isEmpty()) {
            trackContent = new ArrayList<ArrayList<String>>();

            trackName = lContent.get(0);
            numberOfTracks = Integer.parseInt(lContent.get(1));
            bpm = Integer.parseInt(lContent.get(2));
            trackLength = Integer.parseInt(lContent.get(3));
            for (int i = 4; i < lContent.size(); i++) trackContent.add(stringToTrackArray(lContent.get(i)));
        }
        return this;
    }

    @NonNull
    public String toString() {
        StringBuilder info = new StringBuilder();

        info.append(trackName).append("\n");
        info.append(numberOfTracks).append("\n");
        info.append(bpm).append("\n");
        info.append(trackLength).append("\n");
        for (int i = 0; i < numberOfTracks; i++) info.append(trackArrayToString(trackContent.get(i))).append("\n");
        return info.toString();
    }

    public static String trackArrayToString(ArrayList<String> content) {
        StringBuilder returnString = new StringBuilder();
        for (String note : content) returnString.append(note).append(" ");
        returnString.deleteCharAt(returnString.length() - 1);
        return returnString.toString();
    }

    public static ArrayList<String> stringToTrackArray(String content) {
        ArrayList<String> returnList = new ArrayList<String>();
        StringBuilder temp = new StringBuilder();
        for (char partNote : content.toCharArray()) {
            if (partNote != ' ') temp.append(partNote);
            else {
                returnList.add(temp.toString());
                temp = new StringBuilder();
            }
        }
        return returnList;
    }

    public int getNumberOfTracks() {
        return numberOfTracks;
    }

    public void setNumberOfTracks(int numberOfTracks) {
        this.numberOfTracks = numberOfTracks;
    }

    public int getTrackLength() {
        return trackLength;
    }

    public void setTrackLength(int trackLength) {
        this.trackLength = trackLength;
    }

    public int getBpm() {
        return bpm;
    }

    public void setBpm(int bpm) {
        this.bpm = bpm;
    }

    public ArrayList<ArrayList<String>> getTrackContent() {
        return trackContent;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    /*public ArrayList<ArrayList<String>> getFormatedTrack()
    {
        ArrayList<ArrayList<String>> retour = new ArrayList<ArrayList<String>>();

        for (int i = 0; i < numberOfTracks; i++)
        {
            ArrayList<String> track = new ArrayList<String>();
            for (int j = 0; j<trackLength; j++)
            {
                track.add(trackContent.get(trackLength*i+j));
            }
            retour.add(track);
        }
        return retour;
    }*/
}
