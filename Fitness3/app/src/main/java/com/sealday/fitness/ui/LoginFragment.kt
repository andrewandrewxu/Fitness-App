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
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        login.setOnClickListener {
            app.runOnWorkingThread {
                // TODO check username and password
                val userDao = app.appDatabase.userDao()
                val user = userDao.findByName(username.text.toString())
                if (user?.password == password.text.toString()) {
                    var appState = app.appState
                    appState.login = true
                    appState.username = username.text.toString()
                    app.updateAppState(appState)
                    activity?.runOnUiThread {
                        Toast.makeText(activity, "login success.", Toast.LENGTH_SHORT).show()
                        Navigation.findNavController(view).popBackStack(R.id.blankFragment, true)
                    }
                } else {
                    activity?.runOnUiThread {
                        Toast.makeText(activity, "login failure.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        register.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }


}
