package com.sealday.fitness.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "app_state")
data class AppState(
        @PrimaryKey(autoGenerate = true)
        var sid: Int,
        @ColumnInfo(name = "name", index = true)
        var name: String,
        @ColumnInfo(name = "login")
        var login: Boolean,
        @ColumnInfo(name = "username")
        var username: String
)
