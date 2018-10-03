package com.sealday.fitness.ui


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.sealday.fitness.R
import com.sealday.fitness.app
import com.sealday.fitness.db.User
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        register.setOnClickListener {
            app.runOnWorkingThread {
                // TODO check username and password
                val user = User(0, username.text.toString(), password.text.toString(), "")
                val userDao = app.appDatabase.userDao()
                val rUser = userDao.findByName(username.text.toString())
                if (rUser != null) {
                    activity?.runOnUiThread {
                        Toast.makeText(activity, "register failure.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    userDao.insertAll(user)
                    var appState = app.appState
                    appState.login = true
                    appState.username = username.text.toString()
                    app.updateAppState(appState)
                    activity?.runOnUiThread {
                        Toast.makeText(activity, "register success.", Toast.LENGTH_SHORT).show()
                        Navigation.findNavController(view).popBackStack(R.id.blankFragment, true)
                    }
                }
            }
        }
        cancel.setOnClickListener {
            Navigation.findNavController(view).popBackStack()
        }
    }


}

