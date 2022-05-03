package uqac.dim.soundsgood;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {SongEntity.class}, version = 12)
    public abstract class AppBD extends RoomDatabase {

        private static AppBD INSTANCE;
        public abstract SongDao dao();

        public static AppBD getDatabase(Context context) {
            if (INSTANCE == null) {
                INSTANCE =
                        Room.databaseBuilder(context, AppBD.class, "partitiondatabase")
                                // To simplify the exercise, allow queries on the main thread.
                                // Don't do this on a real app!
                                .allowMainThreadQueries()
                                // recreate the database if necessary
                                .fallbackToDestructiveMigration()
                                .build();
            }
            return INSTANCE;
        }

        public static void destroyInstance() {
            INSTANCE = null;
        }
    }
