package com.sealday.fitness.ui


import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.Navigation
import com.sealday.fitness.R
import com.sealday.fitness.app
import kotlinx.android.synthetic.main.fragment_info.*

class InfoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        app.runOnWorkingThread {
            val appState = app.appState
            val user = app.appDatabase.userDao().findByName(appState.username)
            activity?.runOnUiThread {
                username.text = user?.username
                if (user?.location.isNullOrEmpty()) {
                    location.text = "no location for now"
                } else {
                    location.text = user?.location
                }
            }
        }
        username.setOnClickListener {
            val view = LayoutInflater.from(activity).inflate(R.layout.dialog_text, null)
            val text = view.findViewById<EditText>(R.id.text)
            text.setText(username.text)
            AlertDialog
                    .Builder(activity!!)
                    .setTitle("Username")
                    .setView(view)
                    .setPositiveButton("Confirm") { dialog: DialogInterface, which: Int ->
                        app.runOnWorkingThread {
                            val user = app.appDatabase.userDao().findByName(username.text.toString())
                            val appState = app.appState
                            if (user != null) {
                                user.username = text.text.toString()
                                appState.username = text.text.toString()
                                app.appDatabase.userDao().update(user)
                                app.updateAppState(appState)
                            }
                        }
                        username.text = text.text
                    }
                    .setNegativeButton("Cancel") { dialog, which ->
                    }
                    .show()
        }
        password.setOnClickListener {
            val view = LayoutInflater.from(activity).inflate(R.layout.dialog_text, null)
            val text = view.findViewById<EditText>(R.id.text)
            text.setText("")
            AlertDialog
                    .Builder(activity!!)
                    .setTitle("Password")
                    .setView(view)
                    .setPositiveButton("Confirm") { dialog: DialogInterface, which: Int ->
                        app.runOnWorkingThread {
                            val user = app.appDatabase.userDao().findByName(username.text.toString())
                            if (user != null) {
                                user.password = text.text.toString()
                                app.appDatabase.userDao().update(user)
                            }
                        }
                    }
                    .setNegativeButton("Cancel") { dialog, which ->
                    }
                    .show()
        }
        location.setOnClickListener {
            val view = LayoutInflater.from(activity).inflate(R.layout.dialog_text, null)
            val text = view.findViewById<EditText>(R.id.text)
            text.setText(location.text)
            AlertDialog
                    .Builder(activity!!)
                    .setTitle("Location")
                    .setView(view)
                    .setPositiveButton("Confirm") { dialog: DialogInterface, which: Int ->
                        app.runOnWorkingThread {
                            val user = app.appDatabase.userDao().findByName(username.text.toString())
                            if (user != null) {
                                user.location = text.text.toString()
                                app.appDatabase.userDao().update(user)
                            }
                        }
                        location.text = text.text
                    }
                    .setNegativeButton("Cancel") { dialog, which ->
                    }
                    .show()
        }
        logout.setOnClickListener {
            app.runOnWorkingThread {
                var appState = app.appState
                appState.login = false
                app.updateAppState(appState)
                activity?.runOnUiThread {
                    Toast.makeText(activity, "logout success.", Toast.LENGTH_SHORT).show()
                    Navigation.findNavController(view).popBackStack(R.id.blankFragment, true)
                }
            }
        }
    }

}
