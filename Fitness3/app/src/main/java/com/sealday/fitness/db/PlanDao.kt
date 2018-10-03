package com.sealday.fitness.db

import android.arch.persistence.room.*

@Dao
interface PlanDao {

    @get:Query("SELECT * FROM `plan`")
    val all: List<Plan>

    @Insert
    fun insertAll(vararg plans: Plan)

    @Query("SELECT * FROM `plan` WHERE pid = :pid LIMIT 1")
    fun findById(pid: Int): Plan?

    @Delete
    fun delete(plan: Plan)

    @Update
    fun update(vararg plans: Plan)
}