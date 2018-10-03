package com.sealday.fitness.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * TODO save hashed password
 */
@Entity
data class User(
        @PrimaryKey(autoGenerate = true)
        var uid: Int,
        @ColumnInfo(name = "username", index = true)
        var username: String,
        @ColumnInfo(name = "password")
        var password: String,
        @ColumnInfo(name = "location")
        var location: String
)
