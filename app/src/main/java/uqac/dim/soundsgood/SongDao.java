package uqac.dim.soundsgood;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface SongDao {

    @Query("SELECT * FROM SongEntity WHERE SongEntity.tracknom LIKE :Tracknom")
    SongEntity getTrack(String Tracknom);

    @Query("SELECT SongEntity.trackpath FROM SongEntity WHERE SongEntity.tracknom LIKE :Tracknom LIMIT 1")
    String getPath(String Tracknom);

    @Query("DELETE FROM SongEntity WHERE SongEntity.tracknom = :Tracknom")
    void removeTrack(String Tracknom);

    @Query("DELETE FROM SongEntity")
    void clearDatabase();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addTrack(SongEntity nouvelletrack);
}
