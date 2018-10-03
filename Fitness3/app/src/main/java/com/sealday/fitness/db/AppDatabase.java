package com.sealday.fitness.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

/**
 * FIXME Something went wrong with kotlin, so we keep it in Java for now.
 */
@Database(entities = { User.class, AppState.class, Plan.class }, version = 7, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract AppStateDao appStateDao();
    public abstract PlanDao planDao();
    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context){
        if (instance == null) {
            synchronized(AppDatabase.class) {
                if (instance == null) {
                    instance = Room
                            .databaseBuilder(context, AppDatabase.class, "fitness-db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }
}


