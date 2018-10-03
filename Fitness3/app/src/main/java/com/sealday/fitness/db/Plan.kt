package com.sealday.fitness.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity
data class Plan(
        @PrimaryKey(autoGenerate = true)
        var pid: Int,
        @ColumnInfo(name = "date")
        var date: Date,
        @ColumnInfo(name = "name")
        var name: String
)
