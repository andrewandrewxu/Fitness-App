package com.sealday.fitness

import android.app.Application
import android.support.v4.app.Fragment
import com.sealday.fitness.db.AppDatabase
import com.sealday.fitness.db.AppState
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 *
 */
class FitnessApp : Application() {

    private var _appState : AppState? = null
    private var _executor = Executors.newFixedThreadPool(3)

    val appDatabase: AppDatabase
        get() = AppDatabase.getInstance(this)


    val appState: AppState
        get() {
            if (_appState == null) {
                var state = appDatabase.appStateDao().findByName("FitnessApp")
                if (state == null) {
                    state = AppState(0, "FitnessApp", false, "")
                    appDatabase.appStateDao().insertAll(state)
                    _appState = appDatabase.appStateDao().findByName("FitnessApp")
                } else {
                    _appState = state
                }
            }
            return _appState!!
        }

    fun updateAppState(state: AppState) {
        appDatabase.appStateDao().update(state)
    }

    val executor: Executor
        get() = _executor

    fun runOnWorkingThread(command: () -> Unit?) = _executor.execute {
        command()
    }
}

val Fragment.app: FitnessApp
    get() = activity?.application as FitnessApp
