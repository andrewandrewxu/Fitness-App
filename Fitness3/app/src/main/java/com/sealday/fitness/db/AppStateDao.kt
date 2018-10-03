package com.sealday.fitness.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update

@Dao
interface AppStateDao {

    @Query("SELECT * FROM app_state WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): AppState?

    @Insert
    fun insertAll(vararg states: AppState)

    @Update
    fun update(vararg states: AppState)

}
