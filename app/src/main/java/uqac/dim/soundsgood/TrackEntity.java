package uqac.dim.soundsgood;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class TrackEntity {

    @PrimaryKey
    public int tracknum;

    @ColumnInfo(name = "notes")
    public ArrayList<String> notes;

    public TrackEntity() {}

    public TrackEntity(int tracknum, ArrayList<String> notes) {
        this.tracknum = tracknum;
        this.notes = notes;
    }
}
