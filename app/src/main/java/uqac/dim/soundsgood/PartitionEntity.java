package uqac.dim.soundsgood;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class PartitionEntity {
    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "nbtracks")
    public int nbtracks;

    @ColumnInfo(name = "longueur")
    public int longueur;

    @ColumnInfo(name = "bpm")
    public int bpm;

    @ColumnInfo(name = "tracks")
    public ArrayList<TrackEntity> tracks;
}
