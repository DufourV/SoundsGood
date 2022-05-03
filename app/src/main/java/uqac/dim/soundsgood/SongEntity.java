package uqac.dim.soundsgood;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity
public class SongEntity {

    @NonNull
    @PrimaryKey
    public String tracknom = "default";

    @ColumnInfo(name = "trackpath")
    public String trackpath = "default";

    public SongEntity() {}

    public SongEntity(String tracknom, String trackpath)
    {
        this.tracknom = tracknom;
        this.trackpath = trackpath;
    }
}
